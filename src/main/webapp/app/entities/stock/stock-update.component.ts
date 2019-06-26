import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStock, Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';

@Component({
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html'
})
export class StockUpdateComponent implements OnInit {
  isSaving: boolean;

  produits: IProduit[];

  editForm = this.fb.group({
    id: [],
    stockCouvertureMin: [null, [Validators.required]],
    stockCouvertureMax: [null, [Validators.required]],
    stockQte1: [null, [Validators.required, Validators.min(0)]],
    stockQte2: [null, [Validators.min(0)]],
    stockQte3: [null, [Validators.min(0)]],
    stockPrix1: [null, [Validators.required, Validators.min(0)]],
    stockPrix2: [null, [Validators.min(0)]],
    stockPrix3: [null, [Validators.min(0)]],
    stockDatePeremption1: [null, [Validators.required]],
    stockDatePeremption2: [],
    stockDatePeremption3: [],
    stockPrixHT1: [null, [Validators.required, Validators.min(0)]],
    stockPrixHT2: [null, [Validators.min(0)]],
    stockPrixHT3: [],
    stockDateCreation: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockService: StockService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.updateForm(stock);
    });
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stock: IStock) {
    this.editForm.patchValue({
      id: stock.id,
      stockCouvertureMin: stock.stockCouvertureMin,
      stockCouvertureMax: stock.stockCouvertureMax,
      stockQte1: stock.stockQte1,
      stockQte2: stock.stockQte2,
      stockQte3: stock.stockQte3,
      stockPrix1: stock.stockPrix1,
      stockPrix2: stock.stockPrix2,
      stockPrix3: stock.stockPrix3,
      stockDatePeremption1: stock.stockDatePeremption1 != null ? stock.stockDatePeremption1.format(DATE_TIME_FORMAT) : null,
      stockDatePeremption2: stock.stockDatePeremption2 != null ? stock.stockDatePeremption2.format(DATE_TIME_FORMAT) : null,
      stockDatePeremption3: stock.stockDatePeremption3 != null ? stock.stockDatePeremption3.format(DATE_TIME_FORMAT) : null,
      stockPrixHT1: stock.stockPrixHT1,
      stockPrixHT2: stock.stockPrixHT2,
      stockPrixHT3: stock.stockPrixHT3,
      stockDateCreation: stock.stockDateCreation != null ? stock.stockDateCreation.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stock = this.createFromForm();
    if (stock.id !== undefined) {
      this.subscribeToSaveResponse(this.stockService.update(stock));
    } else {
      this.subscribeToSaveResponse(this.stockService.create(stock));
    }
  }

  private createFromForm(): IStock {
    const entity = {
      ...new Stock(),
      id: this.editForm.get(['id']).value,
      stockCouvertureMin: this.editForm.get(['stockCouvertureMin']).value,
      stockCouvertureMax: this.editForm.get(['stockCouvertureMax']).value,
      stockQte1: this.editForm.get(['stockQte1']).value,
      stockQte2: this.editForm.get(['stockQte2']).value,
      stockQte3: this.editForm.get(['stockQte3']).value,
      stockPrix1: this.editForm.get(['stockPrix1']).value,
      stockPrix2: this.editForm.get(['stockPrix2']).value,
      stockPrix3: this.editForm.get(['stockPrix3']).value,
      stockDatePeremption1:
        this.editForm.get(['stockDatePeremption1']).value != null
          ? moment(this.editForm.get(['stockDatePeremption1']).value, DATE_TIME_FORMAT)
          : undefined,
      stockDatePeremption2:
        this.editForm.get(['stockDatePeremption2']).value != null
          ? moment(this.editForm.get(['stockDatePeremption2']).value, DATE_TIME_FORMAT)
          : undefined,
      stockDatePeremption3:
        this.editForm.get(['stockDatePeremption3']).value != null
          ? moment(this.editForm.get(['stockDatePeremption3']).value, DATE_TIME_FORMAT)
          : undefined,
      stockPrixHT1: this.editForm.get(['stockPrixHT1']).value,
      stockPrixHT2: this.editForm.get(['stockPrixHT2']).value,
      stockPrixHT3: this.editForm.get(['stockPrixHT3']).value,
      stockDateCreation:
        this.editForm.get(['stockDateCreation']).value != null
          ? moment(this.editForm.get(['stockDateCreation']).value, DATE_TIME_FORMAT)
          : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStock>>) {
    result.subscribe((res: HttpResponse<IStock>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }
}

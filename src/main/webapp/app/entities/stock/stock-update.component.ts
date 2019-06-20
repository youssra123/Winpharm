import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IStock, Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';

@Component({
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html'
})
export class StockUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    stockCouvertureMin: [null, [Validators.required]],
    stockCouvertureMax: [null, [Validators.required]]
  });

  constructor(protected stockService: StockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.updateForm(stock);
    });
  }

  updateForm(stock: IStock) {
    this.editForm.patchValue({
      id: stock.id,
      stockCouvertureMin: stock.stockCouvertureMin,
      stockCouvertureMax: stock.stockCouvertureMax
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
      stockCouvertureMax: this.editForm.get(['stockCouvertureMax']).value
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
}

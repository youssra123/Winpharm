import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStockproduit, Stockproduit } from 'app/shared/model/stockproduit.model';
import { StockproduitService } from './stockproduit.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';
import { IStock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock';

@Component({
  selector: 'jhi-stockproduit-update',
  templateUrl: './stockproduit-update.component.html'
})
export class StockproduitUpdateComponent implements OnInit {
  isSaving: boolean;

  produits: IProduit[];

  stocks: IStock[];

  editForm = this.fb.group({
    id: [],
    stockProduitQuantite: [null, [Validators.required]],
    stockProduitDateCreation: [null, [Validators.required]],
    stockProduitDatePeremption: [null, [Validators.required]],
    stockProduitPrixVente: [null, [Validators.required]],
    stockProduitPrixHorsTaxe: [null, [Validators.required]],
    stock_produit_produit: [null, Validators.required],
    stock_produit_stock: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockproduitService: StockproduitService,
    protected produitService: ProduitService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockproduit }) => {
      this.updateForm(stockproduit);
    });
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.stockService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStock[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStock[]>) => response.body)
      )
      .subscribe((res: IStock[]) => (this.stocks = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stockproduit: IStockproduit) {
    this.editForm.patchValue({
      id: stockproduit.id,
      stockProduitQuantite: stockproduit.stockProduitQuantite,
      stockProduitDateCreation:
        stockproduit.stockProduitDateCreation != null ? stockproduit.stockProduitDateCreation.format(DATE_TIME_FORMAT) : null,
      stockProduitDatePeremption:
        stockproduit.stockProduitDatePeremption != null ? stockproduit.stockProduitDatePeremption.format(DATE_TIME_FORMAT) : null,
      stockProduitPrixVente: stockproduit.stockProduitPrixVente,
      stockProduitPrixHorsTaxe: stockproduit.stockProduitPrixHorsTaxe,
      stock_produit_produit: stockproduit.stock_produit_produit,
      stock_produit_stock: stockproduit.stock_produit_stock
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockproduit = this.createFromForm();
    if (stockproduit.id !== undefined) {
      this.subscribeToSaveResponse(this.stockproduitService.update(stockproduit));
    } else {
      this.subscribeToSaveResponse(this.stockproduitService.create(stockproduit));
    }
  }

  private createFromForm(): IStockproduit {
    const entity = {
      ...new Stockproduit(),
      id: this.editForm.get(['id']).value,
      stockProduitQuantite: this.editForm.get(['stockProduitQuantite']).value,
      stockProduitDateCreation:
        this.editForm.get(['stockProduitDateCreation']).value != null
          ? moment(this.editForm.get(['stockProduitDateCreation']).value, DATE_TIME_FORMAT)
          : undefined,
      stockProduitDatePeremption:
        this.editForm.get(['stockProduitDatePeremption']).value != null
          ? moment(this.editForm.get(['stockProduitDatePeremption']).value, DATE_TIME_FORMAT)
          : undefined,
      stockProduitPrixVente: this.editForm.get(['stockProduitPrixVente']).value,
      stockProduitPrixHorsTaxe: this.editForm.get(['stockProduitPrixHorsTaxe']).value,
      stock_produit_produit: this.editForm.get(['stock_produit_produit']).value,
      stock_produit_stock: this.editForm.get(['stock_produit_stock']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockproduit>>) {
    result.subscribe((res: HttpResponse<IStockproduit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackStockById(index: number, item: IStock) {
    return item.id;
  }
}

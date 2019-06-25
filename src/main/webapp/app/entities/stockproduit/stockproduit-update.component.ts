import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IStockproduit, Stockproduit } from 'app/shared/model/stockproduit.model';
import { StockproduitService } from './stockproduit.service';

@Component({
  selector: 'jhi-stockproduit-update',
  templateUrl: './stockproduit-update.component.html'
})
export class StockproduitUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected stockproduitService: StockproduitService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockproduit }) => {
      this.updateForm(stockproduit);
    });
  }

  updateForm(stockproduit: IStockproduit) {
    this.editForm.patchValue({
      id: stockproduit.id
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
      id: this.editForm.get(['id']).value
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
}

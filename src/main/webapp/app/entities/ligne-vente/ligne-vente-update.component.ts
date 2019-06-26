import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILigneVente, LigneVente } from 'app/shared/model/ligne-vente.model';
import { LigneVenteService } from './ligne-vente.service';
import { IEnteteVente } from 'app/shared/model/entete-vente.model';
import { EnteteVenteService } from 'app/entities/entete-vente';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';

@Component({
  selector: 'jhi-ligne-vente-update',
  templateUrl: './ligne-vente-update.component.html'
})
export class LigneVenteUpdateComponent implements OnInit {
  isSaving: boolean;

  enteteventes: IEnteteVente[];

  produits: IProduit[];

  editForm = this.fb.group({
    id: [],
    ligneVenteQte: [null, [Validators.required]],
    ligneVenteTotalHT: [null, [Validators.required]],
    ligneVenteTotalTTC: [null, [Validators.required]],
    ligneVentePrixTTC: [null, [Validators.required]],
    ligneVentePrixHT: [null, [Validators.required]],
    ligneVenteDesignation: [null, [Validators.required]],
    enteteVente: [null, Validators.required],
    produit: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ligneVenteService: LigneVenteService,
    protected enteteVenteService: EnteteVenteService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ligneVente }) => {
      this.updateForm(ligneVente);
    });
    this.enteteVenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEnteteVente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEnteteVente[]>) => response.body)
      )
      .subscribe((res: IEnteteVente[]) => (this.enteteventes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ligneVente: ILigneVente) {
    this.editForm.patchValue({
      id: ligneVente.id,
      ligneVenteQte: ligneVente.ligneVenteQte,
      ligneVenteTotalHT: ligneVente.ligneVenteTotalHT,
      ligneVenteTotalTTC: ligneVente.ligneVenteTotalTTC,
      ligneVentePrixTTC: ligneVente.ligneVentePrixTTC,
      ligneVentePrixHT: ligneVente.ligneVentePrixHT,
      ligneVenteDesignation: ligneVente.ligneVenteDesignation,
      enteteVente: ligneVente.enteteVente,
      produit: ligneVente.produit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ligneVente = this.createFromForm();
    if (ligneVente.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneVenteService.update(ligneVente));
    } else {
      this.subscribeToSaveResponse(this.ligneVenteService.create(ligneVente));
    }
  }

  private createFromForm(): ILigneVente {
    const entity = {
      ...new LigneVente(),
      id: this.editForm.get(['id']).value,
      ligneVenteQte: this.editForm.get(['ligneVenteQte']).value,
      ligneVenteTotalHT: this.editForm.get(['ligneVenteTotalHT']).value,
      ligneVenteTotalTTC: this.editForm.get(['ligneVenteTotalTTC']).value,
      ligneVentePrixTTC: this.editForm.get(['ligneVentePrixTTC']).value,
      ligneVentePrixHT: this.editForm.get(['ligneVentePrixHT']).value,
      ligneVenteDesignation: this.editForm.get(['ligneVenteDesignation']).value,
      enteteVente: this.editForm.get(['enteteVente']).value,
      produit: this.editForm.get(['produit']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneVente>>) {
    result.subscribe((res: HttpResponse<ILigneVente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEnteteVenteById(index: number, item: IEnteteVente) {
    return item.id;
  }

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILigneVente, LigneVente } from 'app/shared/model/ligne-vente.model';
import { LigneVenteService } from './ligne-vente.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';
import { IEnteteVente } from 'app/shared/model/entete-vente.model';
import { EnteteVenteService } from 'app/entities/entete-vente';

@Component({
  selector: 'jhi-ligne-vente-update',
  templateUrl: './ligne-vente-update.component.html'
})
export class LigneVenteUpdateComponent implements OnInit {
  isSaving: boolean;

  produits: IProduit[];

  enteteventes: IEnteteVente[];

  editForm = this.fb.group({
    id: [],
    ligneVenteQte: [null, [Validators.required]],
    ligneVenteTotalHT: [],
    ligneVenteTotalTTC: [],
    ligneVentePrixHT: [],
    ligneVentePrixTTC: [],
    ligneVenteDesignation: [],
    produit: [null, Validators.required],
    enteteVente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ligneVenteService: LigneVenteService,
    protected produitService: ProduitService,
    protected enteteVenteService: EnteteVenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ligneVente }) => {
      this.updateForm(ligneVente);
    });
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.enteteVenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEnteteVente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEnteteVente[]>) => response.body)
      )
      .subscribe((res: IEnteteVente[]) => (this.enteteventes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ligneVente: ILigneVente) {
    this.editForm.patchValue({
      id: ligneVente.id,
      ligneVenteQte: ligneVente.ligneVenteQte,
      ligneVenteTotalHT: ligneVente.ligneVenteTotalHT,
      ligneVenteTotalTTC: ligneVente.ligneVenteTotalTTC,
      ligneVentePrixHT: ligneVente.ligneVentePrixHT,
      ligneVentePrixTTC: ligneVente.ligneVentePrixTTC,
      ligneVenteDesignation: ligneVente.ligneVenteDesignation,
      produit: ligneVente.produit,
      enteteVente: ligneVente.enteteVente
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
      ligneVentePrixHT: this.editForm.get(['ligneVentePrixHT']).value,
      ligneVentePrixTTC: this.editForm.get(['ligneVentePrixTTC']).value,
      ligneVenteDesignation: this.editForm.get(['ligneVenteDesignation']).value,
      produit: this.editForm.get(['produit']).value,
      enteteVente: this.editForm.get(['enteteVente']).value
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

  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }

  trackEnteteVenteById(index: number, item: IEnteteVente) {
    return item.id;
  }
}

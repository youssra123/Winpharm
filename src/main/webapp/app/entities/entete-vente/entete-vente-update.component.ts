import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEnteteVente, EnteteVente } from 'app/shared/model/entete-vente.model';
import { EnteteVenteService } from './entete-vente.service';
import { IClient } from 'app/shared/model/client.model';
import { ILigneVente } from 'app/shared/model/ligne-vente.model';
import { ClientService } from 'app/entities/client';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';
@Component({
  selector: 'jhi-entete-vente-update',
  templateUrl: './entete-vente-update.component.html'
})
export class EnteteVenteUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  enteteventes: IEnteteVente[];

  produits: IProduit[];

  editForm = this.fb.group({
    id: [],
    enteteVenteTotalHT: [],
    enteteVenteTotalTTC: [],
    enteteVenteType: [],
    enteteVenteDateCreation: [null, [Validators.required]],
    client: []
  });

  public obj: any;
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected enteteVenteService: EnteteVenteService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    protected produitService: ProduitService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    console.log('*******************************affichage1**********************');
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ enteteVente }) => {
      this.updateForm(enteteVente);
    });
    this.clientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClient[]>) => response.body)
      )
      .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    console.log('*******************************affichage2**********************');
    console.log('*************************clients*************:  ' + this.clients);

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
    console.log('*******************************affichage3**********************');
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
    console.log('*******************************affichage4**********************');
    console.log('*************************produits*************:  ' + this.produits);
  }
  onKey(libelle: string) {
    this.clients = [];
    this.clientService
      .findByDes(libelle, {})
      .subscribe((res: HttpResponse<IClient[]>) => this.paginateClients(res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(enteteVente: IEnteteVente) {
    this.editForm.patchValue({
      id: enteteVente.id,
      enteteVenteTotalHT: enteteVente.enteteVenteTotalHT,
      enteteVenteTotalTTC: enteteVente.enteteVenteTotalTTC,
      enteteVenteType: enteteVente.enteteVenteType,
      enteteVenteDateCreation:
        enteteVente.enteteVenteDateCreation != null ? enteteVente.enteteVenteDateCreation.format(DATE_TIME_FORMAT) : null,
      client: enteteVente.client
    });
  }

  previousState() {
    window.history.back();
  }
  protected paginateClients(data: IClient[]) {
    this.clients = data;
  }
  save() {
    this.isSaving = true;
    this.obj = this.createFromForm();
    console.log('*********************clients*********************:' + this.clients);
    console.log('*********************produits*********************:' + this.produits);
    console.log('clients: ' + this.editForm.get(['client']).value);
    console.log('enteteVenteType: ' + this.editForm.get(['enteteVenteType']).value);
    /* const enteteVente = this.createFromForm();
    if (enteteVente.id !== undefined) {
      this.subscribeToSaveResponse(this.enteteVenteService.update(enteteVente));
    } else {
      this.subscribeToSaveResponse(this.enteteVenteService.create(enteteVente));
    }*/
  }

  private createFromForm(): IEnteteVente {
    const entity = {
      ...new EnteteVente(),
      id: this.editForm.get(['id']).value,
      enteteVenteTotalHT: this.editForm.get(['enteteVenteTotalHT']).value,
      enteteVenteTotalTTC: this.editForm.get(['enteteVenteTotalTTC']).value,
      enteteVenteType: this.editForm.get(['enteteVenteType']).value,
      enteteVenteDateCreation:
        this.editForm.get(['enteteVenteDateCreation']).value != null
          ? moment(this.editForm.get(['enteteVenteDateCreation']).value, DATE_TIME_FORMAT)
          : undefined,
      client: this.editForm.get(['client']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnteteVente>>) {
    result.subscribe((res: HttpResponse<IEnteteVente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackClientById(index: number, item: IClient) {
    return item.id;
  }
  trackProduitById(index: number, item: IProduit) {
    return item.id;
  }
}

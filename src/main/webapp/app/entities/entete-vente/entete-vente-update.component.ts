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
import { ILigneVente, LigneVente } from 'app/shared/model/ligne-vente.model';
import { ClientService } from 'app/entities/client';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit';
import { LigneVenteService } from '../ligne-vente';
@Component({
  selector: 'jhi-entete-vente-update',
  templateUrl: './entete-vente-update.component.html'
})
export class EnteteVenteUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  enteteventes: IEnteteVente[];

  ligneVentes: ILigneVente[];

  produits: IProduit[];

  editForm = this.fb.group({
    id: [],
    enteteVenteTotalHT: [],
    enteteVenteTotalTTC: [],
    enteteVenteType: [],
    enteteVenteDateCreation: [null, [Validators.required]],
    client: []
  });

  public listLV: ILigneVente[] = new Array();
  public lvFromParent: ILigneVente[];
  // les IDs des lignes de ventes supprimées !
  public deletedLVs: number[] = [];

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected enteteVenteService: EnteteVenteService,
    protected ligneVenteService: LigneVenteService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    protected produitService: ProduitService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    console.log('EnteteV : ngOnInit() : start');
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ enteteVente }) => {
      this.updateForm(enteteVente);
    });
    console.log('EnteteV : call client service');
    this.clientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClient[]>) => response.body)
      )
      .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    console.log('EnteteV : call entete-vente service');
    this.enteteVenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEnteteVente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEnteteVente[]>) => response.body)
      )
      .subscribe((res: IEnteteVente[]) => (this.enteteventes = res), (res: HttpErrorResponse) => this.onError(res.message));
    console.log('EnteteV : call ligneVente service');
    this.ligneVenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: ILigneVente[]) => res, (res: HttpErrorResponse) => this.onError(res.message));
    console.log('EnteteV : call produit service');
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
    console.log('EnteteV : ngOnInit() : end');
  }

  sendLigneVentes(data: ILigneVente) {
    console.log('Vente : sendLigneVentes() : ' + JSON.stringify(data));

    if (this.listLV == null) {
      this.listLV = new Array();
    }

    // test if produit existe deja
    for (let i = 0; i < this.listLV.length; i++) {
      if (this.listLV[i].produit === data.produit) {
        this.listLV[i].ligneVenteQte += data.ligneVenteQte;
        return;
      }
    }
    // add ligneVente to the list
    this.listLV.push({
      ...new LigneVente(),
      id: undefined,
      ligneVenteDesignation: null,
      ligneVentePrixHT: 0,
      ligneVentePrixTTC: 0,
      ligneVenteTotalHT: 0,
      ligneVenteTotalTTC: 0,
      ligneVenteQte: data.ligneVenteQte,
      enteteVente: undefined,
      produit: data.produit
    });
    for (let i = 0; i < this.listLV.length; i++) {
      console.log('lv : ' + JSON.stringify(this.listLV[i]));
    }

    if (this.listLV != null) {
      console.log('listLV : working !!!!!!!!');
    } else {
      console.log('listLV : not working ?????????');
    }
  }

  deleteLigneVente(ids: number[]) {
    console.log('produit id received to delete ! - id : ' + ids[0]);
    if (ids.length > 1) {
      console.log('lv id received to delete ! - id : ' + ids[1]);
      this.deletedLVs.push(ids[1]);
      console.log('deletedLVs : ' + this.deletedLVs);
    }
    if (this.listLV != null) {
      for (let i = 0; i < this.listLV.length; i++) {
        if (this.listLV[i].produit.id == ids[0]) {
          this.listLV.splice(i, 1);
          return;
        }
      }
    }
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
    this.lvFromParent = enteteVente.ligneVentes;
    this.listLV = enteteVente.ligneVentes;
    console.log('lvFromParent : ' + enteteVente.ligneVentes);
  }

  previousState() {
    window.history.back();
  }
  protected paginateClients(data: IClient[]) {
    this.clients = data;
  }
  save() {
    this.isSaving = true;
    console.log('*********************clients*********************:' + this.clients);
    console.log('*********************produits*********************:' + this.produits);
    console.log('clients: ' + this.editForm.get(['client']).value);
    console.log('enteteVenteType: ' + this.editForm.get(['enteteVenteType']).value);
    console.log('list ligneVente: ' + this.listLV);
    const enteteVente = this.createFromForm();
    if (enteteVente.id !== undefined) {
      this.subscribeToSaveResponse(this.enteteVenteService.update(enteteVente));
    } else {
      this.subscribeToSaveResponse(this.enteteVenteService.create(enteteVente));
    }
    for (let i = 0; i < this.deletedLVs.length; i++) {
      console.log('delete LV - id : ' + this.deletedLVs[i]);
      console.log(this.ligneVenteService.delete(this.deletedLVs[i]));
      console.log('deleted LV - id : ' + this.deletedLVs[i]);
    }
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
      client: this.editForm.get(['client']).value,
      ligneVentes: this.listLV
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEnteteVente, EnteteVente } from 'app/shared/model/entete-vente.model';
import { EnteteVenteService } from './entete-vente.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
  selector: 'jhi-entete-vente-update',
  templateUrl: './entete-vente-update.component.html'
})
export class EnteteVenteUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  editForm = this.fb.group({
    id: [],
    enteteVenteTotalHT: [null, [Validators.required]],
    enteteVenteTotalTTC: [null, [Validators.required]],
    enteteVenteType: [null, [Validators.required]],
    client: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected enteteVenteService: EnteteVenteService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
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
  }

  updateForm(enteteVente: IEnteteVente) {
    this.editForm.patchValue({
      id: enteteVente.id,
      enteteVenteTotalHT: enteteVente.enteteVenteTotalHT,
      enteteVenteTotalTTC: enteteVente.enteteVenteTotalTTC,
      enteteVenteType: enteteVente.enteteVenteType,
      client: enteteVente.client
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const enteteVente = this.createFromForm();
    if (enteteVente.id !== undefined) {
      this.subscribeToSaveResponse(this.enteteVenteService.update(enteteVente));
    } else {
      this.subscribeToSaveResponse(this.enteteVenteService.create(enteteVente));
    }
  }

  private createFromForm(): IEnteteVente {
    const entity = {
      ...new EnteteVente(),
      id: this.editForm.get(['id']).value,
      enteteVenteTotalHT: this.editForm.get(['enteteVenteTotalHT']).value,
      enteteVenteTotalTTC: this.editForm.get(['enteteVenteTotalTTC']).value,
      enteteVenteType: this.editForm.get(['enteteVenteType']).value,
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
}

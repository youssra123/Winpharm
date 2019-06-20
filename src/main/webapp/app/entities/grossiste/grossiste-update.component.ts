import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGrossiste, Grossiste } from 'app/shared/model/grossiste.model';
import { GrossisteService } from './grossiste.service';
import { IVille } from 'app/shared/model/ville.model';
import { VilleService } from 'app/entities/ville';

@Component({
  selector: 'jhi-grossiste-update',
  templateUrl: './grossiste-update.component.html'
})
export class GrossisteUpdateComponent implements OnInit {
  isSaving: boolean;

  villes: IVille[];

  editForm = this.fb.group({
    id: [],
    grossisteRaisSoc: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
    grossisteAdresse: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(40)]],
    grossisteTelephone: [],
    grossis_ville: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected grossisteService: GrossisteService,
    protected villeService: VilleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ grossiste }) => {
      this.updateForm(grossiste);
    });
    this.villeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVille[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVille[]>) => response.body)
      )
      .subscribe((res: IVille[]) => (this.villes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(grossiste: IGrossiste) {
    this.editForm.patchValue({
      id: grossiste.id,
      grossisteRaisSoc: grossiste.grossisteRaisSoc,
      grossisteAdresse: grossiste.grossisteAdresse,
      grossisteTelephone: grossiste.grossisteTelephone,
      grossis_ville: grossiste.grossis_ville
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const grossiste = this.createFromForm();
    if (grossiste.id !== undefined) {
      this.subscribeToSaveResponse(this.grossisteService.update(grossiste));
    } else {
      this.subscribeToSaveResponse(this.grossisteService.create(grossiste));
    }
  }

  private createFromForm(): IGrossiste {
    const entity = {
      ...new Grossiste(),
      id: this.editForm.get(['id']).value,
      grossisteRaisSoc: this.editForm.get(['grossisteRaisSoc']).value,
      grossisteAdresse: this.editForm.get(['grossisteAdresse']).value,
      grossisteTelephone: this.editForm.get(['grossisteTelephone']).value,
      grossis_ville: this.editForm.get(['grossis_ville']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrossiste>>) {
    result.subscribe((res: HttpResponse<IGrossiste>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackVilleById(index: number, item: IVille) {
    return item.id;
  }
}

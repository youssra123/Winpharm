import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVille, Ville } from 'app/shared/model/ville.model';
import { VilleService } from './ville.service';

@Component({
  selector: 'jhi-ville-update',
  templateUrl: './ville-update.component.html'
})
export class VilleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    villeLibelle: []
  });

  constructor(protected villeService: VilleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ville }) => {
      this.updateForm(ville);
    });
  }

  updateForm(ville: IVille) {
    this.editForm.patchValue({
      id: ville.id,
      villeLibelle: ville.villeLibelle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ville = this.createFromForm();
    if (ville.id !== undefined) {
      this.subscribeToSaveResponse(this.villeService.update(ville));
    } else {
      this.subscribeToSaveResponse(this.villeService.create(ville));
    }
  }

  private createFromForm(): IVille {
    const entity = {
      ...new Ville(),
      id: this.editForm.get(['id']).value,
      villeLibelle: this.editForm.get(['villeLibelle']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVille>>) {
    result.subscribe((res: HttpResponse<IVille>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRayon, Rayon } from 'app/shared/model/rayon.model';
import { RayonService } from './rayon.service';

@Component({
  selector: 'jhi-rayon-update',
  templateUrl: './rayon-update.component.html',
  styleUrls: ['rayon.scss']
})
export class RayonUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    rayonLibelle: [null, [Validators.required]]
  });

  constructor(protected rayonService: RayonService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rayon }) => {
      this.updateForm(rayon);
    });
  }

  updateForm(rayon: IRayon) {
    this.editForm.patchValue({
      id: rayon.id,
      rayonLibelle: rayon.rayonLibelle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rayon = this.createFromForm();
    if (rayon.id !== undefined) {
      this.subscribeToSaveResponse(this.rayonService.update(rayon));
    } else {
      this.subscribeToSaveResponse(this.rayonService.create(rayon));
    }
  }

  private createFromForm(): IRayon {
    const entity = {
      ...new Rayon(),
      id: this.editForm.get(['id']).value,
      rayonLibelle: this.editForm.get(['rayonLibelle']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRayon>>) {
    result.subscribe((res: HttpResponse<IRayon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

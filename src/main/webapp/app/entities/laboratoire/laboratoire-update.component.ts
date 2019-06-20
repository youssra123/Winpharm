import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILaboratoire, Laboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from './laboratoire.service';

@Component({
  selector: 'jhi-laboratoire-update',
  templateUrl: './laboratoire-update.component.html'
})
export class LaboratoireUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    laboratoireRaisSoc: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
    laboratoireAdresse: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(40)]],
    laboratoireTelephone: [null, [Validators.required, Validators.min(10), Validators.max(10)]]
  });

  constructor(protected laboratoireService: LaboratoireService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ laboratoire }) => {
      this.updateForm(laboratoire);
    });
  }

  updateForm(laboratoire: ILaboratoire) {
    this.editForm.patchValue({
      id: laboratoire.id,
      laboratoireRaisSoc: laboratoire.laboratoireRaisSoc,
      laboratoireAdresse: laboratoire.laboratoireAdresse,
      laboratoireTelephone: laboratoire.laboratoireTelephone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const laboratoire = this.createFromForm();
    if (laboratoire.id !== undefined) {
      this.subscribeToSaveResponse(this.laboratoireService.update(laboratoire));
    } else {
      this.subscribeToSaveResponse(this.laboratoireService.create(laboratoire));
    }
  }

  private createFromForm(): ILaboratoire {
    const entity = {
      ...new Laboratoire(),
      id: this.editForm.get(['id']).value,
      laboratoireRaisSoc: this.editForm.get(['laboratoireRaisSoc']).value,
      laboratoireAdresse: this.editForm.get(['laboratoireAdresse']).value,
      laboratoireTelephone: this.editForm.get(['laboratoireTelephone']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILaboratoire>>) {
    result.subscribe((res: HttpResponse<ILaboratoire>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

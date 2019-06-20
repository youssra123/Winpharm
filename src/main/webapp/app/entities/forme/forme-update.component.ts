import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IForme, Forme } from 'app/shared/model/forme.model';
import { FormeService } from './forme.service';

@Component({
  selector: 'jhi-forme-update',
  templateUrl: './forme-update.component.html'
})
export class FormeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    formeLibelle: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(20)]]
  });

  constructor(protected formeService: FormeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ forme }) => {
      this.updateForm(forme);
    });
  }

  updateForm(forme: IForme) {
    this.editForm.patchValue({
      id: forme.id,
      formeLibelle: forme.formeLibelle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const forme = this.createFromForm();
    if (forme.id !== undefined) {
      this.subscribeToSaveResponse(this.formeService.update(forme));
    } else {
      this.subscribeToSaveResponse(this.formeService.create(forme));
    }
  }

  private createFromForm(): IForme {
    const entity = {
      ...new Forme(),
      id: this.editForm.get(['id']).value,
      formeLibelle: this.editForm.get(['formeLibelle']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IForme>>) {
    result.subscribe((res: HttpResponse<IForme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

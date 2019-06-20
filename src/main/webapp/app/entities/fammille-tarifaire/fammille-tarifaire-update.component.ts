import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFammilleTarifaire, FammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';
import { FammilleTarifaireService } from './fammille-tarifaire.service';

@Component({
  selector: 'jhi-fammille-tarifaire-update',
  templateUrl: './fammille-tarifaire-update.component.html'
})
export class FammilleTarifaireUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    famiTarifLibelle: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
    famiTarifForfait: [null, [Validators.required]],
    famiTarifCodeTVA: [],
    famiTarifTauxTVA: []
  });

  constructor(
    protected fammilleTarifaireService: FammilleTarifaireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fammilleTarifaire }) => {
      this.updateForm(fammilleTarifaire);
    });
  }

  updateForm(fammilleTarifaire: IFammilleTarifaire) {
    this.editForm.patchValue({
      id: fammilleTarifaire.id,
      famiTarifLibelle: fammilleTarifaire.famiTarifLibelle,
      famiTarifForfait: fammilleTarifaire.famiTarifForfait,
      famiTarifCodeTVA: fammilleTarifaire.famiTarifCodeTVA,
      famiTarifTauxTVA: fammilleTarifaire.famiTarifTauxTVA
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fammilleTarifaire = this.createFromForm();
    if (fammilleTarifaire.id !== undefined) {
      this.subscribeToSaveResponse(this.fammilleTarifaireService.update(fammilleTarifaire));
    } else {
      this.subscribeToSaveResponse(this.fammilleTarifaireService.create(fammilleTarifaire));
    }
  }

  private createFromForm(): IFammilleTarifaire {
    const entity = {
      ...new FammilleTarifaire(),
      id: this.editForm.get(['id']).value,
      famiTarifLibelle: this.editForm.get(['famiTarifLibelle']).value,
      famiTarifForfait: this.editForm.get(['famiTarifForfait']).value,
      famiTarifCodeTVA: this.editForm.get(['famiTarifCodeTVA']).value,
      famiTarifTauxTVA: this.editForm.get(['famiTarifTauxTVA']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFammilleTarifaire>>) {
    result.subscribe((res: HttpResponse<IFammilleTarifaire>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

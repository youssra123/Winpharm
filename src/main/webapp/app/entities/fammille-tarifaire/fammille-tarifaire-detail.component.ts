import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';

@Component({
  selector: 'jhi-fammille-tarifaire-detail',
  templateUrl: './fammille-tarifaire-detail.component.html'
})
export class FammilleTarifaireDetailComponent implements OnInit {
  fammilleTarifaire: IFammilleTarifaire;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fammilleTarifaire }) => {
      this.fammilleTarifaire = fammilleTarifaire;
    });
  }

  previousState() {
    window.history.back();
  }
}

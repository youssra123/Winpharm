import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILaboratoire } from 'app/shared/model/laboratoire.model';

@Component({
  selector: 'jhi-laboratoire-detail',
  templateUrl: './laboratoire-detail.component.html'
})
export class LaboratoireDetailComponent implements OnInit {
  laboratoire: ILaboratoire;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ laboratoire }) => {
      this.laboratoire = laboratoire;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrossiste } from 'app/shared/model/grossiste.model';

@Component({
  selector: 'jhi-grossiste-detail',
  templateUrl: './grossiste-detail.component.html'
})
export class GrossisteDetailComponent implements OnInit {
  grossiste: IGrossiste;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grossiste }) => {
      this.grossiste = grossiste;
    });
  }

  previousState() {
    window.history.back();
  }
}

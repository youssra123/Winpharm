import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRayon } from 'app/shared/model/rayon.model';

@Component({
  selector: 'jhi-rayon-detail',
  templateUrl: './rayon-detail.component.html'
})
export class RayonDetailComponent implements OnInit {
  rayon: IRayon;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rayon }) => {
      this.rayon = rayon;
    });
  }

  previousState() {
    window.history.back();
  }
}

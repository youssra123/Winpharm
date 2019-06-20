import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IForme } from 'app/shared/model/forme.model';

@Component({
  selector: 'jhi-forme-detail',
  templateUrl: './forme-detail.component.html'
})
export class FormeDetailComponent implements OnInit {
  forme: IForme;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ forme }) => {
      this.forme = forme;
    });
  }

  previousState() {
    window.history.back();
  }
}

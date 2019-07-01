import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnteteVente } from 'app/shared/model/entete-vente.model';

@Component({
  selector: 'jhi-entete-vente-detail',
  templateUrl: './entete-vente-detail.component.html',
  styleUrls: ['enteteVente.scss']
})
export class EnteteVenteDetailComponent implements OnInit {
  enteteVente: IEnteteVente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ enteteVente }) => {
      this.enteteVente = enteteVente;
    });
  }

  previousState() {
    window.history.back();
  }
}

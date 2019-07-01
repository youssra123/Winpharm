import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneVente } from 'app/shared/model/ligne-vente.model';

@Component({
  selector: 'jhi-ligne-vente-detail',
  templateUrl: './ligne-vente-detail.component.html',
  styles: [
    `
      .bottom {
        margin-bottom: 4%;
        padding-left: 8%;
      }
      .text-content {
        font-weight: bold;
        font-size: 18px;
      }
    `
  ]
})
export class LigneVenteDetailComponent implements OnInit {
  ligneVente: ILigneVente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ligneVente }) => {
      this.ligneVente = ligneVente;
    });
  }

  previousState() {
    window.history.back();
  }
}

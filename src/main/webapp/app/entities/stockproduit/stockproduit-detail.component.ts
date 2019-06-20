import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockproduit } from 'app/shared/model/stockproduit.model';

@Component({
  selector: 'jhi-stockproduit-detail',
  templateUrl: './stockproduit-detail.component.html'
})
export class StockproduitDetailComponent implements OnInit {
  stockproduit: IStockproduit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockproduit }) => {
      this.stockproduit = stockproduit;
    });
  }

  previousState() {
    window.history.back();
  }
}

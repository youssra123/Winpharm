import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStock } from 'app/shared/model/stock.model';

@Component({
  selector: 'jhi-stock-detail',
  templateUrl: './stock-detail.component.html',
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
      .col {
        color: #00a46c;
        font-weight: bold;
      }
    `
  ]
})
export class StockDetailComponent implements OnInit {
  stock: IStock;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.stock = stock;
    });
  }

  previousState() {
    window.history.back();
  }
}

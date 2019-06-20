import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';
import { IStock } from 'app/shared/model/stock.model';

export interface IStockproduit {
  id?: number;
  stockProduitQuantite?: number;
  stockProduitDateCreation?: Moment;
  stockProduitDatePeremption?: Moment;
  stockProduitPrixVente?: number;
  stockProduitPrixHorsTaxe?: number;
  stock_produit_produit?: IProduit;
  stock_produit_stock?: IStock;
}

export class Stockproduit implements IStockproduit {
  constructor(
    public id?: number,
    public stockProduitQuantite?: number,
    public stockProduitDateCreation?: Moment,
    public stockProduitDatePeremption?: Moment,
    public stockProduitPrixVente?: number,
    public stockProduitPrixHorsTaxe?: number,
    public stock_produit_produit?: IProduit,
    public stock_produit_stock?: IStock
  ) {}
}

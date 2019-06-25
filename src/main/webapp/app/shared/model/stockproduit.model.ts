export interface IStockproduit {
  id?: number;
}

export class Stockproduit implements IStockproduit {
  constructor(public id?: number) {}
}

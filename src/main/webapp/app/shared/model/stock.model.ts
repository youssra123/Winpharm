export interface IStock {
  id?: number;
  stockCouvertureMin?: number;
  stockCouvertureMax?: number;
}

export class Stock implements IStock {
  constructor(public id?: number, public stockCouvertureMin?: number, public stockCouvertureMax?: number) {}
}

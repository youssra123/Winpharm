import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';

export interface IStock {
  id?: number;
  stockCouvertureMin?: number;
  stockCouvertureMax?: number;
  stockQte1?: number;
  stockQte2?: number;
  stockQte3?: number;
  stockPrix1?: number;
  stockPrix2?: number;
  stockPrix3?: number;
  stockDatePeremption1?: Moment;
  stockDatePeremption2?: Moment;
  stockDatePeremption3?: Moment;
  stockPrixHT1?: number;
  stockPrixHT2?: number;
  stockPrixHT3?: number;
  stockDateCreation?: Moment;
  produit?: IProduit;
}

export class Stock implements IStock {
  constructor(
    public id?: number,
    public stockCouvertureMin?: number,
    public stockCouvertureMax?: number,
    public stockQte1?: number,
    public stockQte2?: number,
    public stockQte3?: number,
    public stockPrix1?: number,
    public stockPrix2?: number,
    public stockPrix3?: number,
    public stockDatePeremption1?: Moment,
    public stockDatePeremption2?: Moment,
    public stockDatePeremption3?: Moment,
    public stockPrixHT1?: number,
    public stockPrixHT2?: number,
    public stockPrixHT3?: number,
    public stockDateCreation?: Moment,
    public produit?: IProduit
  ) {}
}

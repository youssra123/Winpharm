export interface IForme {
  id?: number;
  formeLibelle?: string;
}

export class Forme implements IForme {
  constructor(public id?: number, public formeLibelle?: string) {}
}

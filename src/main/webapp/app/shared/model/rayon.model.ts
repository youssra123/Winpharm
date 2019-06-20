export interface IRayon {
  id?: number;
  rayonLibelle?: string;
}

export class Rayon implements IRayon {
  constructor(public id?: number, public rayonLibelle?: string) {}
}

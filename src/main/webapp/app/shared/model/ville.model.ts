export interface IVille {
  id?: number;
  villeLibelle?: string;
}

export class Ville implements IVille {
  constructor(public id?: number, public villeLibelle?: string) {}
}

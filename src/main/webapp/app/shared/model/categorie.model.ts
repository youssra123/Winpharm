export interface ICategorie {
  id?: number;
  categorieLibelle?: string;
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public categorieLibelle?: string) {}
}

import { IEnteteVente } from 'app/shared/model/entete-vente.model';
import { IProduit } from 'app/shared/model/produit.model';

export interface ILigneVente {
  id?: number;
  ligneVenteQte?: number;
  ligneVenteTotalHT?: number;
  ligneVenteTotalTTC?: number;
  ligneVentePrixTTC?: number;
  ligneVentePrixHT?: number;
  ligneVenteDesignation?: string;
  enteteVente?: IEnteteVente;
  produit?: IProduit;
}

export class LigneVente implements ILigneVente {
  constructor(
    public id?: number,
    public ligneVenteQte?: number,
    public ligneVenteTotalHT?: number,
    public ligneVenteTotalTTC?: number,
    public ligneVentePrixTTC?: number,
    public ligneVentePrixHT?: number,
    public ligneVenteDesignation?: string,
    public enteteVente?: IEnteteVente,
    public produit?: IProduit
  ) {}
}

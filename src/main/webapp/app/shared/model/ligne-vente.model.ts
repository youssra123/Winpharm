import { IProduit } from 'app/shared/model/produit.model';
import { IEnteteVente } from 'app/shared/model/entete-vente.model';

export interface ILigneVente {
  id?: number;
  ligneVenteQte?: number;
  ligneVenteTotalHT?: number;
  ligneVenteTotalTTC?: number;
  ligneVentePrixHT?: number;
  ligneVentePrixTTC?: number;
  ligneVenteDesignation?: string;
  produit?: IProduit;
  enteteVente?: IEnteteVente;
}

export class LigneVente implements ILigneVente {
  constructor(
    public id?: number,
    public ligneVenteQte?: number,
    public ligneVenteTotalHT?: number,
    public ligneVenteTotalTTC?: number,
    public ligneVentePrixHT?: number,
    public ligneVentePrixTTC?: number,
    public ligneVenteDesignation?: string,
    public produit?: IProduit,
    public enteteVente?: IEnteteVente
  ) {}
}

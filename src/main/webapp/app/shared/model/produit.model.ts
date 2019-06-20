import { IRayon } from 'app/shared/model/rayon.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { IFammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';
import { IGrossiste } from 'app/shared/model/grossiste.model';
import { IForme } from 'app/shared/model/forme.model';

export interface IProduit {
  id?: number;
  produitLibelle?: string;
  produitActif?: string;
  produitCodeBarre?: number;
  produitDosage?: number;
  produitUniDosage?: string;
  produitVolume?: number;
  produitUniVolume?: string;
  produit_rayon?: IRayon;
  produit_categorie?: ICategorie;
  produit_fam_tar?: IFammilleTarifaire;
  produit_laboratoire?: ILaboratoire;
  produit_grossiste?: IGrossiste;
  proform?: IForme;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public produitLibelle?: string,
    public produitActif?: string,
    public produitCodeBarre?: number,
    public produitDosage?: number,
    public produitUniDosage?: string,
    public produitVolume?: number,
    public produitUniVolume?: string,
    public produit_rayon?: IRayon,
    public produit_categorie?: ICategorie,
    public produit_fam_tar?: IFammilleTarifaire,
    public produit_laboratoire?: ILaboratoire,
    public produit_grossiste?: IGrossiste,
    public proform?: IForme
  ) {}
}

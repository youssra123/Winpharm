import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';
import { ILigneVente } from 'app/shared/model/ligne-vente.model';

export interface IEnteteVente {
  id?: number;
  enteteVenteTotalHT?: number;
  enteteVenteTotalTTC?: number;
  enteteVenteType?: string;
  enteteVenteDateCreation?: Moment;
  client?: IClient;
  ligneVentes?: ILigneVente[];
}

export class EnteteVente implements IEnteteVente {
  constructor(
    public id?: number,
    public enteteVenteTotalHT?: number,
    public enteteVenteTotalTTC?: number,
    public enteteVenteType?: string,
    public enteteVenteDateCreation?: Moment,
    public client?: IClient,
    public ligneVentes?: ILigneVente[]
  ) {}
}

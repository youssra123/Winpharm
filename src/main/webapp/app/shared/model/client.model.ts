import { IEnteteVente } from 'app/shared/model/entete-vente.model';

export interface IClient {
  id?: number;
  clientNom?: string;
  clientTelephone?: number;
  clientAdresse?: string;
  enteteVentes?: IEnteteVente[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public clientNom?: string,
    public clientTelephone?: number,
    public clientAdresse?: string,
    public enteteVentes?: IEnteteVente[]
  ) {}
}

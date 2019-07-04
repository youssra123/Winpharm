import { IEnteteVente } from 'app/shared/model/entete-vente.model';

export interface IClient {
  id?: number;
  clientNom?: string;
  clientAdresse?: string;
  clientTelephone?: string;
  enteteVentes?: IEnteteVente[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public clientNom?: string,
    public clientAdresse?: string,
    public clientTelephone?: string,
    public enteteVentes?: IEnteteVente[]
  ) {}
}

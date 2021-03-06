import { IVille } from 'app/shared/model/ville.model';

export interface IGrossiste {
  id?: number;
  grossisteRaisSoc?: string;
  grossisteAdresse?: string;
  grossisteTelephone?: string;
  grossis_ville?: IVille;
}

export class Grossiste implements IGrossiste {
  constructor(
    public id?: number,
    public grossisteRaisSoc?: string,
    public grossisteAdresse?: string,
    public grossisteTelephone?: string,
    public grossis_ville?: IVille
  ) {}
}

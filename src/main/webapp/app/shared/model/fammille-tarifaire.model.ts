export interface IFammilleTarifaire {
  id?: number;
  famiTarifLibelle?: string;
  famiTarifForfait?: number;
  famiTarifCodeTVA?: number;
  famiTarifTauxTVA?: number;
}

export class FammilleTarifaire implements IFammilleTarifaire {
  constructor(
    public id?: number,
    public famiTarifLibelle?: string,
    public famiTarifForfait?: number,
    public famiTarifCodeTVA?: number,
    public famiTarifTauxTVA?: number
  ) {}
}

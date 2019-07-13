import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILigneVente } from 'app/shared/model/ligne-vente.model';

type EntityResponseType = HttpResponse<ILigneVente>;
type EntityArrayResponseType = HttpResponse<ILigneVente[]>;

@Injectable({ providedIn: 'root' })
export class LigneVenteService {
  public resourceUrl = SERVER_API_URL + 'api/ligne-ventes';

  constructor(protected http: HttpClient) {}

  create(ligneVente: ILigneVente): Observable<EntityResponseType> {
    return this.http.post<ILigneVente>(this.resourceUrl, ligneVente, { observe: 'response' });
  }

  update(ligneVente: ILigneVente): Observable<EntityResponseType> {
    return this.http.put<ILigneVente>(this.resourceUrl, ligneVente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneVente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneVente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    console.log('receive : delete LV - id : ' + id);
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

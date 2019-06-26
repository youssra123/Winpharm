import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnteteVente } from 'app/shared/model/entete-vente.model';

type EntityResponseType = HttpResponse<IEnteteVente>;
type EntityArrayResponseType = HttpResponse<IEnteteVente[]>;

@Injectable({ providedIn: 'root' })
export class EnteteVenteService {
  public resourceUrl = SERVER_API_URL + 'api/entete-ventes';

  constructor(protected http: HttpClient) {}

  create(enteteVente: IEnteteVente): Observable<EntityResponseType> {
    return this.http.post<IEnteteVente>(this.resourceUrl, enteteVente, { observe: 'response' });
  }

  update(enteteVente: IEnteteVente): Observable<EntityResponseType> {
    return this.http.put<IEnteteVente>(this.resourceUrl, enteteVente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnteteVente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnteteVente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

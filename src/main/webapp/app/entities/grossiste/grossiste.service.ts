import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrossiste } from 'app/shared/model/grossiste.model';

type EntityResponseType = HttpResponse<IGrossiste>;
type EntityArrayResponseType = HttpResponse<IGrossiste[]>;

@Injectable({ providedIn: 'root' })
export class GrossisteService {
  public resourceUrl = SERVER_API_URL + 'api/grossistes';

  constructor(protected http: HttpClient) {}

  create(grossiste: IGrossiste): Observable<EntityResponseType> {
    return this.http.post<IGrossiste>(this.resourceUrl, grossiste, { observe: 'response' });
  }

  update(grossiste: IGrossiste): Observable<EntityResponseType> {
    return this.http.put<IGrossiste>(this.resourceUrl, grossiste, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrossiste>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  findByDes(libelle: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrossiste[]>(`${this.resourceUrl}?q=${libelle}`, { params: options, observe: 'response' });
  }
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrossiste[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';

type EntityResponseType = HttpResponse<IFammilleTarifaire>;
type EntityArrayResponseType = HttpResponse<IFammilleTarifaire[]>;

@Injectable({ providedIn: 'root' })
export class FammilleTarifaireService {
  public resourceUrl = SERVER_API_URL + 'api/fammille-tarifaires';

  constructor(protected http: HttpClient) {}

  create(fammilleTarifaire: IFammilleTarifaire): Observable<EntityResponseType> {
    return this.http.post<IFammilleTarifaire>(this.resourceUrl, fammilleTarifaire, { observe: 'response' });
  }

  update(fammilleTarifaire: IFammilleTarifaire): Observable<EntityResponseType> {
    return this.http.put<IFammilleTarifaire>(this.resourceUrl, fammilleTarifaire, { observe: 'response' });
  }
  findByDes(libelle: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFammilleTarifaire[]>(`${this.resourceUrl}?q=${libelle}`, { params: options, observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFammilleTarifaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFammilleTarifaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

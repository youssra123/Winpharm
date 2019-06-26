import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';

type EntityResponseType = HttpResponse<ILaboratoire>;
type EntityArrayResponseType = HttpResponse<ILaboratoire[]>;

@Injectable({ providedIn: 'root' })
export class LaboratoireService {
  public resourceUrl = SERVER_API_URL + 'api/laboratoires';

  constructor(protected http: HttpClient) {}

  create(laboratoire: ILaboratoire): Observable<EntityResponseType> {
    return this.http.post<ILaboratoire>(this.resourceUrl, laboratoire, { observe: 'response' });
  }

  update(laboratoire: ILaboratoire): Observable<EntityResponseType> {
    return this.http.put<ILaboratoire>(this.resourceUrl, laboratoire, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILaboratoire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  findByDes(libelle: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILaboratoire[]>(`${this.resourceUrl}?q=${libelle}`, { params: options, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILaboratoire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

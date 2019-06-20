import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IForme } from 'app/shared/model/forme.model';

type EntityResponseType = HttpResponse<IForme>;
type EntityArrayResponseType = HttpResponse<IForme[]>;

@Injectable({ providedIn: 'root' })
export class FormeService {
  public resourceUrl = SERVER_API_URL + 'api/formes';

  constructor(protected http: HttpClient) {}

  create(forme: IForme): Observable<EntityResponseType> {
    return this.http.post<IForme>(this.resourceUrl, forme, { observe: 'response' });
  }

  update(forme: IForme): Observable<EntityResponseType> {
    return this.http.put<IForme>(this.resourceUrl, forme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IForme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IForme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRayon } from 'app/shared/model/rayon.model';

type EntityResponseType = HttpResponse<IRayon>;
type EntityArrayResponseType = HttpResponse<IRayon[]>;

@Injectable({ providedIn: 'root' })
export class RayonService {
  public resourceUrl = SERVER_API_URL + 'api/rayons';

  constructor(protected http: HttpClient) {}

  create(rayon: IRayon): Observable<EntityResponseType> {
    return this.http.post<IRayon>(this.resourceUrl, rayon, { observe: 'response' });
  }

  update(rayon: IRayon): Observable<EntityResponseType> {
    return this.http.put<IRayon>(this.resourceUrl, rayon, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRayon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRayon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

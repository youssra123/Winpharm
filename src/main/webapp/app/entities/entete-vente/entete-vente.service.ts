import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

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
    const copy = this.convertDateFromClient(enteteVente);
    return this.http
      .post<IEnteteVente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(enteteVente: IEnteteVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enteteVente);
    return this.http
      .put<IEnteteVente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEnteteVente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnteteVente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(enteteVente: IEnteteVente): IEnteteVente {
    const copy: IEnteteVente = Object.assign({}, enteteVente, {
      enteteVenteDateCreation:
        enteteVente.enteteVenteDateCreation != null && enteteVente.enteteVenteDateCreation.isValid()
          ? enteteVente.enteteVenteDateCreation.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.enteteVenteDateCreation = res.body.enteteVenteDateCreation != null ? moment(res.body.enteteVenteDateCreation) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((enteteVente: IEnteteVente) => {
        enteteVente.enteteVenteDateCreation =
          enteteVente.enteteVenteDateCreation != null ? moment(enteteVente.enteteVenteDateCreation) : null;
      });
    }
    return res;
  }
}

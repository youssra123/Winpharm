import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockproduit } from 'app/shared/model/stockproduit.model';

type EntityResponseType = HttpResponse<IStockproduit>;
type EntityArrayResponseType = HttpResponse<IStockproduit[]>;

@Injectable({ providedIn: 'root' })
export class StockproduitService {
  public resourceUrl = SERVER_API_URL + 'api/stockproduits';

  constructor(protected http: HttpClient) {}

  create(stockproduit: IStockproduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockproduit);
    return this.http
      .post<IStockproduit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stockproduit: IStockproduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockproduit);
    return this.http
      .put<IStockproduit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStockproduit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockproduit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  findByDes(libelle: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStockproduit[]>(`${this.resourceUrl}?q=${libelle}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stockproduit: IStockproduit): IStockproduit {
    const copy: IStockproduit = Object.assign({}, stockproduit, {
      stockProduitDateCreation:
        stockproduit.stockProduitDateCreation != null && stockproduit.stockProduitDateCreation.isValid()
          ? stockproduit.stockProduitDateCreation.toJSON()
          : null,
      stockProduitDatePeremption:
        stockproduit.stockProduitDatePeremption != null && stockproduit.stockProduitDatePeremption.isValid()
          ? stockproduit.stockProduitDatePeremption.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.stockProduitDateCreation = res.body.stockProduitDateCreation != null ? moment(res.body.stockProduitDateCreation) : null;
      res.body.stockProduitDatePeremption =
        res.body.stockProduitDatePeremption != null ? moment(res.body.stockProduitDatePeremption) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stockproduit: IStockproduit) => {
        stockproduit.stockProduitDateCreation =
          stockproduit.stockProduitDateCreation != null ? moment(stockproduit.stockProduitDateCreation) : null;
        stockproduit.stockProduitDatePeremption =
          stockproduit.stockProduitDatePeremption != null ? moment(stockproduit.stockProduitDatePeremption) : null;
      });
    }
    return res;
  }
}

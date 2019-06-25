import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStock } from 'app/shared/model/stock.model';

type EntityResponseType = HttpResponse<IStock>;
type EntityArrayResponseType = HttpResponse<IStock[]>;

@Injectable({ providedIn: 'root' })
export class StockService {
  public resourceUrl = SERVER_API_URL + 'api/stocks';

  constructor(protected http: HttpClient) {}

  create(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .post<IStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .put<IStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stock: IStock): IStock {
    const copy: IStock = Object.assign({}, stock, {
      stockDatePeremption1:
        stock.stockDatePeremption1 != null && stock.stockDatePeremption1.isValid() ? stock.stockDatePeremption1.toJSON() : null,
      stockDatePeremption2:
        stock.stockDatePeremption2 != null && stock.stockDatePeremption2.isValid() ? stock.stockDatePeremption2.toJSON() : null,
      stockDatePeremption3:
        stock.stockDatePeremption3 != null && stock.stockDatePeremption3.isValid() ? stock.stockDatePeremption3.toJSON() : null,
      stockDateCreation: stock.stockDateCreation != null && stock.stockDateCreation.isValid() ? stock.stockDateCreation.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.stockDatePeremption1 = res.body.stockDatePeremption1 != null ? moment(res.body.stockDatePeremption1) : null;
      res.body.stockDatePeremption2 = res.body.stockDatePeremption2 != null ? moment(res.body.stockDatePeremption2) : null;
      res.body.stockDatePeremption3 = res.body.stockDatePeremption3 != null ? moment(res.body.stockDatePeremption3) : null;
      res.body.stockDateCreation = res.body.stockDateCreation != null ? moment(res.body.stockDateCreation) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stock: IStock) => {
        stock.stockDatePeremption1 = stock.stockDatePeremption1 != null ? moment(stock.stockDatePeremption1) : null;
        stock.stockDatePeremption2 = stock.stockDatePeremption2 != null ? moment(stock.stockDatePeremption2) : null;
        stock.stockDatePeremption3 = stock.stockDatePeremption3 != null ? moment(stock.stockDatePeremption3) : null;
        stock.stockDateCreation = stock.stockDateCreation != null ? moment(stock.stockDateCreation) : null;
      });
    }
    return res;
  }
}

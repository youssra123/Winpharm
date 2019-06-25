import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IStockproduit>(this.resourceUrl, stockproduit, { observe: 'response' });
  }

  update(stockproduit: IStockproduit): Observable<EntityResponseType> {
    return this.http.put<IStockproduit>(this.resourceUrl, stockproduit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStockproduit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStockproduit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

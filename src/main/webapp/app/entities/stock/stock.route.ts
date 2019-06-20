import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockUpdateComponent } from './stock-update.component';
import { StockDeletePopupComponent } from './stock-delete-dialog.component';
import { IStock } from 'app/shared/model/stock.model';

@Injectable({ providedIn: 'root' })
export class StockResolve implements Resolve<IStock> {
  constructor(private service: StockService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStock> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Stock>) => response.ok),
        map((stock: HttpResponse<Stock>) => stock.body)
      );
    }
    return of(new Stock());
  }
}

export const stockRoute: Routes = [
  {
    path: '',
    component: StockComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockDetailComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stockPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StockDeletePopupComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

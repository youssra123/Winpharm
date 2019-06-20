import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Stockproduit } from 'app/shared/model/stockproduit.model';
import { StockproduitService } from './stockproduit.service';
import { StockproduitComponent } from './stockproduit.component';
import { StockproduitDetailComponent } from './stockproduit-detail.component';
import { StockproduitUpdateComponent } from './stockproduit-update.component';
import { StockproduitDeletePopupComponent } from './stockproduit-delete-dialog.component';
import { IStockproduit } from 'app/shared/model/stockproduit.model';

@Injectable({ providedIn: 'root' })
export class StockproduitResolve implements Resolve<IStockproduit> {
  constructor(private service: StockproduitService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockproduit> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Stockproduit>) => response.ok),
        map((stockproduit: HttpResponse<Stockproduit>) => stockproduit.body)
      );
    }
    return of(new Stockproduit());
  }
}

export const stockproduitRoute: Routes = [
  {
    path: '',
    component: StockproduitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stockproduit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockproduitDetailComponent,
    resolve: {
      stockproduit: StockproduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stockproduit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockproduitUpdateComponent,
    resolve: {
      stockproduit: StockproduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stockproduit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockproduitUpdateComponent,
    resolve: {
      stockproduit: StockproduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stockproduit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stockproduitPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StockproduitDeletePopupComponent,
    resolve: {
      stockproduit: StockproduitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.stockproduit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

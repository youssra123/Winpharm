import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EnteteVente } from 'app/shared/model/entete-vente.model';
import { EnteteVenteService } from './entete-vente.service';
import { EnteteVenteComponent } from './entete-vente.component';
import { EnteteVenteDetailComponent } from './entete-vente-detail.component';
import { EnteteVenteUpdateComponent } from './entete-vente-update.component';
import { EnteteVenteDeletePopupComponent } from './entete-vente-delete-dialog.component';
import { IEnteteVente } from 'app/shared/model/entete-vente.model';
import { LigneVenteComponent } from '../ligne-vente/ligne-vente.component';

@Injectable({ providedIn: 'root' })
export class EnteteVenteResolve implements Resolve<IEnteteVente> {
  constructor(private service: EnteteVenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEnteteVente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EnteteVente>) => response.ok),
        map((enteteVente: HttpResponse<EnteteVente>) => enteteVente.body)
      );
    }
    return of(new EnteteVente());
  }
}

export const enteteVenteRoute: Routes = [
  {
    path: '',
    component: EnteteVenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'winpharmApp.enteteVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnteteVenteDetailComponent,
    resolve: {
      enteteVente: EnteteVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.enteteVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnteteVenteUpdateComponent,
    resolve: {
      enteteVente: EnteteVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.enteteVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnteteVenteUpdateComponent,
    resolve: {
      enteteVente: EnteteVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.enteteVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const enteteVentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EnteteVenteDeletePopupComponent,
    resolve: {
      enteteVente: EnteteVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.enteteVente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

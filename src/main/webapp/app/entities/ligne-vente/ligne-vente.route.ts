import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LigneVente } from 'app/shared/model/ligne-vente.model';
import { LigneVenteService } from './ligne-vente.service';
import { LigneVenteComponent } from './ligne-vente.component';
import { LigneVenteDetailComponent } from './ligne-vente-detail.component';
import { LigneVenteUpdateComponent } from './ligne-vente-update.component';
import { LigneVenteDeletePopupComponent } from './ligne-vente-delete-dialog.component';
import { ILigneVente } from 'app/shared/model/ligne-vente.model';

@Injectable({ providedIn: 'root' })
export class LigneVenteResolve implements Resolve<ILigneVente> {
  constructor(private service: LigneVenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILigneVente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LigneVente>) => response.ok),
        map((ligneVente: HttpResponse<LigneVente>) => ligneVente.body)
      );
    }
    return of(new LigneVente());
  }
}

export const ligneVenteRoute: Routes = [
  {
    path: '',
    component: LigneVenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'winpharmApp.ligneVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LigneVenteDetailComponent,
    resolve: {
      ligneVente: LigneVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.ligneVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/voire',
    component: LigneVenteDetailComponent,
    resolve: {
      ligneVente: LigneVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.ligneVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LigneVenteUpdateComponent,
    resolve: {
      ligneVente: LigneVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.ligneVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LigneVenteUpdateComponent,
    resolve: {
      ligneVente: LigneVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.ligneVente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ligneVentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LigneVenteDeletePopupComponent,
    resolve: {
      ligneVente: LigneVenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.ligneVente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

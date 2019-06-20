import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Laboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from './laboratoire.service';
import { LaboratoireComponent } from './laboratoire.component';
import { LaboratoireDetailComponent } from './laboratoire-detail.component';
import { LaboratoireUpdateComponent } from './laboratoire-update.component';
import { LaboratoireDeletePopupComponent } from './laboratoire-delete-dialog.component';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';

@Injectable({ providedIn: 'root' })
export class LaboratoireResolve implements Resolve<ILaboratoire> {
  constructor(private service: LaboratoireService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILaboratoire> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Laboratoire>) => response.ok),
        map((laboratoire: HttpResponse<Laboratoire>) => laboratoire.body)
      );
    }
    return of(new Laboratoire());
  }
}

export const laboratoireRoute: Routes = [
  {
    path: '',
    component: LaboratoireComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.laboratoire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LaboratoireDetailComponent,
    resolve: {
      laboratoire: LaboratoireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.laboratoire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LaboratoireUpdateComponent,
    resolve: {
      laboratoire: LaboratoireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.laboratoire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LaboratoireUpdateComponent,
    resolve: {
      laboratoire: LaboratoireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.laboratoire.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const laboratoirePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LaboratoireDeletePopupComponent,
    resolve: {
      laboratoire: LaboratoireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.laboratoire.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

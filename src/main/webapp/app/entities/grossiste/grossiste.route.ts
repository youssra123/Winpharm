import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Grossiste } from 'app/shared/model/grossiste.model';
import { GrossisteService } from './grossiste.service';
import { GrossisteComponent } from './grossiste.component';
import { GrossisteDetailComponent } from './grossiste-detail.component';
import { GrossisteUpdateComponent } from './grossiste-update.component';
import { GrossisteDeletePopupComponent } from './grossiste-delete-dialog.component';
import { IGrossiste } from 'app/shared/model/grossiste.model';

@Injectable({ providedIn: 'root' })
export class GrossisteResolve implements Resolve<IGrossiste> {
  constructor(private service: GrossisteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGrossiste> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Grossiste>) => response.ok),
        map((grossiste: HttpResponse<Grossiste>) => grossiste.body)
      );
    }
    return of(new Grossiste());
  }
}

export const grossisteRoute: Routes = [
  {
    path: '',
    component: GrossisteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.grossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GrossisteDetailComponent,
    resolve: {
      grossiste: GrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.grossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GrossisteUpdateComponent,
    resolve: {
      grossiste: GrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.grossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GrossisteUpdateComponent,
    resolve: {
      grossiste: GrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.grossiste.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const grossistePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GrossisteDeletePopupComponent,
    resolve: {
      grossiste: GrossisteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.grossiste.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rayon } from 'app/shared/model/rayon.model';
import { RayonService } from './rayon.service';
import { RayonComponent } from './rayon.component';
import { RayonDetailComponent } from './rayon-detail.component';
import { RayonUpdateComponent } from './rayon-update.component';
import { RayonDeletePopupComponent } from './rayon-delete-dialog.component';
import { IRayon } from 'app/shared/model/rayon.model';

@Injectable({ providedIn: 'root' })
export class RayonResolve implements Resolve<IRayon> {
  constructor(private service: RayonService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRayon> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Rayon>) => response.ok),
        map((rayon: HttpResponse<Rayon>) => rayon.body)
      );
    }
    return of(new Rayon());
  }
}

export const rayonRoute: Routes = [
  {
    path: '',
    component: RayonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RayonDetailComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RayonUpdateComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RayonUpdateComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rayonPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RayonDeletePopupComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

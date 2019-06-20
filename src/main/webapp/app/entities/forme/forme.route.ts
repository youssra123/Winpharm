import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Forme } from 'app/shared/model/forme.model';
import { FormeService } from './forme.service';
import { FormeComponent } from './forme.component';
import { FormeDetailComponent } from './forme-detail.component';
import { FormeUpdateComponent } from './forme-update.component';
import { FormeDeletePopupComponent } from './forme-delete-dialog.component';
import { IForme } from 'app/shared/model/forme.model';

@Injectable({ providedIn: 'root' })
export class FormeResolve implements Resolve<IForme> {
  constructor(private service: FormeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IForme> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Forme>) => response.ok),
        map((forme: HttpResponse<Forme>) => forme.body)
      );
    }
    return of(new Forme());
  }
}

export const formeRoute: Routes = [
  {
    path: '',
    component: FormeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.forme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormeDetailComponent,
    resolve: {
      forme: FormeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.forme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormeUpdateComponent,
    resolve: {
      forme: FormeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.forme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormeUpdateComponent,
    resolve: {
      forme: FormeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.forme.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const formePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FormeDeletePopupComponent,
    resolve: {
      forme: FormeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.forme.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

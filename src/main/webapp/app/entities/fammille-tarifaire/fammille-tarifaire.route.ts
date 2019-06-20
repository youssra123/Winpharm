import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';
import { FammilleTarifaireService } from './fammille-tarifaire.service';
import { FammilleTarifaireComponent } from './fammille-tarifaire.component';
import { FammilleTarifaireDetailComponent } from './fammille-tarifaire-detail.component';
import { FammilleTarifaireUpdateComponent } from './fammille-tarifaire-update.component';
import { FammilleTarifaireDeletePopupComponent } from './fammille-tarifaire-delete-dialog.component';
import { IFammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';

@Injectable({ providedIn: 'root' })
export class FammilleTarifaireResolve implements Resolve<IFammilleTarifaire> {
  constructor(private service: FammilleTarifaireService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFammilleTarifaire> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FammilleTarifaire>) => response.ok),
        map((fammilleTarifaire: HttpResponse<FammilleTarifaire>) => fammilleTarifaire.body)
      );
    }
    return of(new FammilleTarifaire());
  }
}

export const fammilleTarifaireRoute: Routes = [
  {
    path: '',
    component: FammilleTarifaireComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.fammilleTarifaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FammilleTarifaireDetailComponent,
    resolve: {
      fammilleTarifaire: FammilleTarifaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.fammilleTarifaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FammilleTarifaireUpdateComponent,
    resolve: {
      fammilleTarifaire: FammilleTarifaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.fammilleTarifaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FammilleTarifaireUpdateComponent,
    resolve: {
      fammilleTarifaire: FammilleTarifaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.fammilleTarifaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fammilleTarifairePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FammilleTarifaireDeletePopupComponent,
    resolve: {
      fammilleTarifaire: FammilleTarifaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'winpharmApp.fammilleTarifaire.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

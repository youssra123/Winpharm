import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  EnteteVenteComponent,
  EnteteVenteDetailComponent,
  EnteteVenteUpdateComponent,
  EnteteVenteDeletePopupComponent,
  EnteteVenteDeleteDialogComponent,
  enteteVenteRoute,
  enteteVentePopupRoute
} from './';
import {
  LigneVenteComponent,
  LigneVenteDetailComponent,
  LigneVenteUpdateComponent,
  LigneVente2DetailComponent,
  LigneVente2UpdateComponent
  //LigneVenteDeletePopupComponent,
  //LigneVenteDeleteDialogComponent,
  //ligneVenteRoute,
  //ligneVentePopupRoute
} from '../ligne-vente/';
import { WinpharmEntityModule } from 'app/entities/entity.module';

const ENTITY_STATES = [...enteteVenteRoute, ...enteteVentePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES), WinpharmEntityModule],
  declarations: [
    EnteteVenteComponent,
    EnteteVenteDetailComponent,
    EnteteVenteUpdateComponent,
    LigneVenteComponent,
    LigneVenteUpdateComponent,
    LigneVenteDetailComponent,
    LigneVente2DetailComponent,
    LigneVente2UpdateComponent,
    EnteteVenteDeleteDialogComponent,
    EnteteVenteDeletePopupComponent
  ],
  entryComponents: [
    LigneVenteComponent,
    LigneVenteUpdateComponent,
    EnteteVenteComponent,
    LigneVente2UpdateComponent,
    EnteteVenteUpdateComponent,
    EnteteVenteDeleteDialogComponent,
    EnteteVenteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmEnteteVenteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

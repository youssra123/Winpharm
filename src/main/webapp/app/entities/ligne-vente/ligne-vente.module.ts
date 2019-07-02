import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  LigneVenteComponent,
  LigneVenteDetailComponent,
  LigneVenteUpdateComponent,
  LigneVenteDeletePopupComponent,
  LigneVenteDeleteDialogComponent,
  ligneVenteRoute,
  LigneVente2DetailComponent,
  LigneVente2UpdateComponent,
  ligneVentePopupRoute
} from './';

const ENTITY_STATES = [...ligneVenteRoute, ...ligneVentePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LigneVenteComponent,
    LigneVenteDetailComponent,
    LigneVenteUpdateComponent,
    LigneVente2DetailComponent,
    LigneVenteDeleteDialogComponent,
    LigneVente2UpdateComponent,
    LigneVenteDeletePopupComponent
  ],
  entryComponents: [
    LigneVenteComponent,
    LigneVente2DetailComponent,
    LigneVenteUpdateComponent,
    LigneVenteDeleteDialogComponent,
    LigneVenteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmLigneVenteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

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
  ligneVentePopupRoute
} from './';

const ENTITY_STATES = [...ligneVenteRoute, ...ligneVentePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LigneVenteComponent,
    LigneVenteDetailComponent,
    LigneVenteUpdateComponent,
    LigneVenteDeleteDialogComponent,
    LigneVenteDeletePopupComponent
  ],
  entryComponents: [LigneVenteComponent, LigneVenteUpdateComponent, LigneVenteDeleteDialogComponent, LigneVenteDeletePopupComponent],
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

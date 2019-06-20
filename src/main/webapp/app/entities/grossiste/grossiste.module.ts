import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  GrossisteComponent,
  GrossisteDetailComponent,
  GrossisteUpdateComponent,
  GrossisteDeletePopupComponent,
  GrossisteDeleteDialogComponent,
  grossisteRoute,
  grossistePopupRoute
} from './';

const ENTITY_STATES = [...grossisteRoute, ...grossistePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GrossisteComponent,
    GrossisteDetailComponent,
    GrossisteUpdateComponent,
    GrossisteDeleteDialogComponent,
    GrossisteDeletePopupComponent
  ],
  entryComponents: [GrossisteComponent, GrossisteUpdateComponent, GrossisteDeleteDialogComponent, GrossisteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmGrossisteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

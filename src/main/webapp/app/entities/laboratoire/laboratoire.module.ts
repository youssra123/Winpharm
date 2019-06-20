import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  LaboratoireComponent,
  LaboratoireDetailComponent,
  LaboratoireUpdateComponent,
  LaboratoireDeletePopupComponent,
  LaboratoireDeleteDialogComponent,
  laboratoireRoute,
  laboratoirePopupRoute
} from './';

const ENTITY_STATES = [...laboratoireRoute, ...laboratoirePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LaboratoireComponent,
    LaboratoireDetailComponent,
    LaboratoireUpdateComponent,
    LaboratoireDeleteDialogComponent,
    LaboratoireDeletePopupComponent
  ],
  entryComponents: [LaboratoireComponent, LaboratoireUpdateComponent, LaboratoireDeleteDialogComponent, LaboratoireDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmLaboratoireModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  FammilleTarifaireComponent,
  FammilleTarifaireDetailComponent,
  FammilleTarifaireUpdateComponent,
  FammilleTarifaireDeletePopupComponent,
  FammilleTarifaireDeleteDialogComponent,
  fammilleTarifaireRoute,
  fammilleTarifairePopupRoute
} from './';

const ENTITY_STATES = [...fammilleTarifaireRoute, ...fammilleTarifairePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FammilleTarifaireComponent,
    FammilleTarifaireDetailComponent,
    FammilleTarifaireUpdateComponent,
    FammilleTarifaireDeleteDialogComponent,
    FammilleTarifaireDeletePopupComponent
  ],
  entryComponents: [
    FammilleTarifaireComponent,
    FammilleTarifaireUpdateComponent,
    FammilleTarifaireDeleteDialogComponent,
    FammilleTarifaireDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmFammilleTarifaireModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

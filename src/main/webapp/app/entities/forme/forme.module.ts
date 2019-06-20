import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  FormeComponent,
  FormeDetailComponent,
  FormeUpdateComponent,
  FormeDeletePopupComponent,
  FormeDeleteDialogComponent,
  formeRoute,
  formePopupRoute
} from './';

const ENTITY_STATES = [...formeRoute, ...formePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FormeComponent, FormeDetailComponent, FormeUpdateComponent, FormeDeleteDialogComponent, FormeDeletePopupComponent],
  entryComponents: [FormeComponent, FormeUpdateComponent, FormeDeleteDialogComponent, FormeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmFormeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

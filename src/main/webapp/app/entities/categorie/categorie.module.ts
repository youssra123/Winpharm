import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  CategorieComponent,
  CategorieDetailComponent,
  CategorieUpdateComponent,
  CategorieDeletePopupComponent,
  CategorieDeleteDialogComponent,
  categorieRoute,
  categoriePopupRoute
} from './';

const ENTITY_STATES = [...categorieRoute, ...categoriePopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategorieComponent,
    CategorieDetailComponent,
    CategorieUpdateComponent,
    CategorieDeleteDialogComponent,
    CategorieDeletePopupComponent
  ],
  entryComponents: [CategorieComponent, CategorieUpdateComponent, CategorieDeleteDialogComponent, CategorieDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmCategorieModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

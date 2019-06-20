import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  StockproduitComponent,
  StockproduitDetailComponent,
  StockproduitUpdateComponent,
  StockproduitDeletePopupComponent,
  StockproduitDeleteDialogComponent,
  stockproduitRoute,
  stockproduitPopupRoute
} from './';

const ENTITY_STATES = [...stockproduitRoute, ...stockproduitPopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StockproduitComponent,
    StockproduitDetailComponent,
    StockproduitUpdateComponent,
    StockproduitDeleteDialogComponent,
    StockproduitDeletePopupComponent
  ],
  entryComponents: [
    StockproduitComponent,
    StockproduitUpdateComponent,
    StockproduitDeleteDialogComponent,
    StockproduitDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmStockproduitModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

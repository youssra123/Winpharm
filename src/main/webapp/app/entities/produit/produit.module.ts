import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  ProduitComponent,
  ProduitDetailComponent,
  ProduitUpdateComponent,
  ProduitDeletePopupComponent,
  ProduitDeleteDialogComponent,
  produitRoute,
  produitPopupRoute
} from './';
import { WinpharmEntityModule } from '../entity.module';
import { StockUpdateComponent } from '../stock/stock-update.component';
const ENTITY_STATES = [...produitRoute, ...produitPopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES), WinpharmEntityModule],
  declarations: [
    ProduitComponent,
    ProduitDetailComponent,
    ProduitUpdateComponent,
    ProduitDeleteDialogComponent,
    StockUpdateComponent,
    ProduitDeletePopupComponent
  ],
  entryComponents: [ProduitComponent, ProduitUpdateComponent, ProduitDeleteDialogComponent, ProduitDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmProduitModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

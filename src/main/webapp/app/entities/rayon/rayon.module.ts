import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { WinpharmSharedModule } from 'app/shared';
import {
  RayonComponent,
  RayonDetailComponent,
  RayonUpdateComponent,
  RayonDeletePopupComponent,
  RayonDeleteDialogComponent,
  rayonRoute,
  rayonPopupRoute
} from './';

const ENTITY_STATES = [...rayonRoute, ...rayonPopupRoute];

@NgModule({
  imports: [WinpharmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RayonComponent, RayonDetailComponent, RayonUpdateComponent, RayonDeleteDialogComponent, RayonDeletePopupComponent],
  entryComponents: [RayonComponent, RayonUpdateComponent, RayonDeleteDialogComponent, RayonDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmRayonModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

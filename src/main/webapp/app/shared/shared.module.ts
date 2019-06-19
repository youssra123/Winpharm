import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { WinpharmSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [WinpharmSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [WinpharmSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmSharedModule {
  static forRoot() {
    return {
      ngModule: WinpharmSharedModule
    };
  }
}

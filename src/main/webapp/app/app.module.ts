import './vendor.ts';

import { NgModule, Injector, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { WinpharmSharedModule } from 'app/shared';
import { WinpharmCoreModule } from 'app/core';
import { WinpharmAppRoutingModule } from './app-routing.module';
import { WinpharmHomeModule } from './home/home.module';
import { WinpharmAccountModule } from './account/account.module';
import { WinpharmEntityModule } from './entities/entity.module';
import * as moment from 'moment';
import { WinpharmAppContactModule } from './contact/contact.module';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
  imports: [
    BrowserModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000,
      i18nEnabled: true,
      defaultI18nLang: 'fr'
    }),
    WinpharmSharedModule.forRoot(),
    WinpharmCoreModule,
    WinpharmHomeModule,
    WinpharmAccountModule,
    WinpharmAppContactModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    WinpharmEntityModule,
    MDBBootstrapModule.forRoot(),
    WinpharmAppRoutingModule
  ],
  schemas: [NO_ERRORS_SCHEMA],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    }
  ],
  bootstrap: [JhiMainComponent]
})
export class WinpharmAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}

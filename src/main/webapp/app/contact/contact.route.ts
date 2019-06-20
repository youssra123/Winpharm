import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ContactComponent } from './contact.component';

export const CONTACT_ROUTE: Route = {
  path: 'contact',
  component: ContactComponent,
  data: {
    authorities: [],
    pageTitle: 'contact.title'
  },
  canActivate: [UserRouteAccessService]
};

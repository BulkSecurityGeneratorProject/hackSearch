import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { HiThereComponent } from './hi-there.component';

export const HI_THERE_ROUTE: Route = {
    path: 'hi-there',
    component: HiThereComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'hi-there.title'
    },
    canActivate: [UserRouteAccessService]
};

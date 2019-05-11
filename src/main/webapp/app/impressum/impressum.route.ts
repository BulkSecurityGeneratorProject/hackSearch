import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ImpressumComponent } from './impressum.component';

export const IMPRESSUM_ROUTE: Route = {
    path: 'impressum',
    component: ImpressumComponent,
    data: {
        authorities: [],
        pageTitle: 'impressum.title'
    },
    canActivate: [UserRouteAccessService]
};

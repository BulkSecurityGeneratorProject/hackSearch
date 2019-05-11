import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CaptionMood } from 'app/shared/model/caption-mood.model';
import { CaptionMoodService } from './caption-mood.service';
import { CaptionMoodComponent } from './caption-mood.component';
import { CaptionMoodDetailComponent } from './caption-mood-detail.component';
import { CaptionMoodUpdateComponent } from './caption-mood-update.component';
import { CaptionMoodDeletePopupComponent } from './caption-mood-delete-dialog.component';
import { ICaptionMood } from 'app/shared/model/caption-mood.model';

@Injectable({ providedIn: 'root' })
export class CaptionMoodResolve implements Resolve<ICaptionMood> {
    constructor(private service: CaptionMoodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICaptionMood> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CaptionMood>) => response.ok),
                map((caption: HttpResponse<CaptionMood>) => caption.body)
            );
        }
        return of(new CaptionMood());
    }
}

export const captionRoute: Routes = [
    {
        path: '',
        component: CaptionMoodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.caption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CaptionMoodDetailComponent,
        resolve: {
            caption: CaptionMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.caption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CaptionMoodUpdateComponent,
        resolve: {
            caption: CaptionMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.caption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CaptionMoodUpdateComponent,
        resolve: {
            caption: CaptionMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.caption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const captionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CaptionMoodDeletePopupComponent,
        resolve: {
            caption: CaptionMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.caption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

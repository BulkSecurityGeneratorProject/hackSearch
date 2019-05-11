import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TranslationLineMood } from 'app/shared/model/translation-line-mood.model';
import { TranslationLineMoodService } from './translation-line-mood.service';
import { TranslationLineMoodComponent } from './translation-line-mood.component';
import { TranslationLineMoodDetailComponent } from './translation-line-mood-detail.component';
import { TranslationLineMoodUpdateComponent } from './translation-line-mood-update.component';
import { TranslationLineMoodDeletePopupComponent } from './translation-line-mood-delete-dialog.component';
import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';

@Injectable({ providedIn: 'root' })
export class TranslationLineMoodResolve implements Resolve<ITranslationLineMood> {
    constructor(private service: TranslationLineMoodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITranslationLineMood> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TranslationLineMood>) => response.ok),
                map((translationLine: HttpResponse<TranslationLineMood>) => translationLine.body)
            );
        }
        return of(new TranslationLineMood());
    }
}

export const translationLineRoute: Routes = [
    {
        path: '',
        component: TranslationLineMoodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.translationLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TranslationLineMoodDetailComponent,
        resolve: {
            translationLine: TranslationLineMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.translationLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TranslationLineMoodUpdateComponent,
        resolve: {
            translationLine: TranslationLineMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.translationLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TranslationLineMoodUpdateComponent,
        resolve: {
            translationLine: TranslationLineMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.translationLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const translationLinePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TranslationLineMoodDeletePopupComponent,
        resolve: {
            translationLine: TranslationLineMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.translationLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

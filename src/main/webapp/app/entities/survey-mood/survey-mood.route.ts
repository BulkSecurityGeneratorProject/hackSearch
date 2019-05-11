import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SurveyMood } from 'app/shared/model/survey-mood.model';
import { SurveyMoodService } from './survey-mood.service';
import { SurveyMoodComponent } from './survey-mood.component';
import { SurveyMoodDetailComponent } from './survey-mood-detail.component';
import { SurveyMoodUpdateComponent } from './survey-mood-update.component';
import { SurveyMoodDeletePopupComponent } from './survey-mood-delete-dialog.component';
import { ISurveyMood } from 'app/shared/model/survey-mood.model';

@Injectable({ providedIn: 'root' })
export class SurveyMoodResolve implements Resolve<ISurveyMood> {
    constructor(private service: SurveyMoodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISurveyMood> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SurveyMood>) => response.ok),
                map((survey: HttpResponse<SurveyMood>) => survey.body)
            );
        }
        return of(new SurveyMood());
    }
}

export const surveyRoute: Routes = [
    {
        path: '',
        component: SurveyMoodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.survey.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SurveyMoodDetailComponent,
        resolve: {
            survey: SurveyMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.survey.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SurveyMoodUpdateComponent,
        resolve: {
            survey: SurveyMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.survey.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SurveyMoodUpdateComponent,
        resolve: {
            survey: SurveyMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.survey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const surveyPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SurveyMoodDeletePopupComponent,
        resolve: {
            survey: SurveyMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.survey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

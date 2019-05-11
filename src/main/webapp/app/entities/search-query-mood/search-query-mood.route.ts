import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SearchQueryMood } from 'app/shared/model/search-query-mood.model';
import { SearchQueryMoodService } from './search-query-mood.service';
import { SearchQueryMoodComponent } from './search-query-mood.component';
import { SearchQueryMoodDetailComponent } from './search-query-mood-detail.component';
import { SearchQueryMoodUpdateComponent } from './search-query-mood-update.component';
import { SearchQueryMoodDeletePopupComponent } from './search-query-mood-delete-dialog.component';
import { ISearchQueryMood } from 'app/shared/model/search-query-mood.model';

@Injectable({ providedIn: 'root' })
export class SearchQueryMoodResolve implements Resolve<ISearchQueryMood> {
    constructor(private service: SearchQueryMoodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISearchQueryMood> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SearchQueryMood>) => response.ok),
                map((searchQuery: HttpResponse<SearchQueryMood>) => searchQuery.body)
            );
        }
        return of(new SearchQueryMood());
    }
}

export const searchQueryRoute: Routes = [
    {
        path: '',
        component: SearchQueryMoodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.searchQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SearchQueryMoodDetailComponent,
        resolve: {
            searchQuery: SearchQueryMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.searchQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SearchQueryMoodUpdateComponent,
        resolve: {
            searchQuery: SearchQueryMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.searchQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SearchQueryMoodUpdateComponent,
        resolve: {
            searchQuery: SearchQueryMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.searchQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const searchQueryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SearchQueryMoodDeletePopupComponent,
        resolve: {
            searchQuery: SearchQueryMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.searchQuery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

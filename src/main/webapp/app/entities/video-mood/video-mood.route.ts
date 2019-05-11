import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VideoMood } from 'app/shared/model/video-mood.model';
import { VideoMoodService } from './video-mood.service';
import { VideoMoodComponent } from './video-mood.component';
import { VideoMoodDetailComponent } from './video-mood-detail.component';
import { VideoMoodUpdateComponent } from './video-mood-update.component';
import { VideoMoodDeletePopupComponent } from './video-mood-delete-dialog.component';
import { IVideoMood } from 'app/shared/model/video-mood.model';

@Injectable({ providedIn: 'root' })
export class VideoMoodResolve implements Resolve<IVideoMood> {
    constructor(private service: VideoMoodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVideoMood> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VideoMood>) => response.ok),
                map((video: HttpResponse<VideoMood>) => video.body)
            );
        }
        return of(new VideoMood());
    }
}

export const videoRoute: Routes = [
    {
        path: '',
        component: VideoMoodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VideoMoodDetailComponent,
        resolve: {
            video: VideoMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VideoMoodUpdateComponent,
        resolve: {
            video: VideoMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VideoMoodUpdateComponent,
        resolve: {
            video: VideoMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const videoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VideoMoodDeletePopupComponent,
        resolve: {
            video: VideoMoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hackSearchApp.video.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

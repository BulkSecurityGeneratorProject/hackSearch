import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVideoMood } from 'app/shared/model/video-mood.model';
import { AccountService } from 'app/core';
import { VideoMoodService } from './video-mood.service';

@Component({
    selector: 'jhi-video-mood',
    templateUrl: './video-mood.component.html'
})
export class VideoMoodComponent implements OnInit, OnDestroy {
    videos: IVideoMood[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected videoService: VideoMoodService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.videoService
            .query()
            .pipe(
                filter((res: HttpResponse<IVideoMood[]>) => res.ok),
                map((res: HttpResponse<IVideoMood[]>) => res.body)
            )
            .subscribe(
                (res: IVideoMood[]) => {
                    this.videos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVideos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVideoMood) {
        return item.id;
    }

    registerChangeInVideos() {
        this.eventSubscriber = this.eventManager.subscribe('videoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

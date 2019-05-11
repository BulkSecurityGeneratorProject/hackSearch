import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICaptionMood } from 'app/shared/model/caption-mood.model';
import { AccountService } from 'app/core';
import { CaptionMoodService } from './caption-mood.service';

@Component({
    selector: 'jhi-caption-mood',
    templateUrl: './caption-mood.component.html'
})
export class CaptionMoodComponent implements OnInit, OnDestroy {
    captions: ICaptionMood[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected captionService: CaptionMoodService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.captionService
            .query()
            .pipe(
                filter((res: HttpResponse<ICaptionMood[]>) => res.ok),
                map((res: HttpResponse<ICaptionMood[]>) => res.body)
            )
            .subscribe(
                (res: ICaptionMood[]) => {
                    this.captions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCaptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICaptionMood) {
        return item.id;
    }

    registerChangeInCaptions() {
        this.eventSubscriber = this.eventManager.subscribe('captionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

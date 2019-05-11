import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISurveyMood } from 'app/shared/model/survey-mood.model';
import { AccountService } from 'app/core';
import { SurveyMoodService } from './survey-mood.service';

@Component({
    selector: 'jhi-survey-mood',
    templateUrl: './survey-mood.component.html'
})
export class SurveyMoodComponent implements OnInit, OnDestroy {
    surveys: ISurveyMood[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected surveyService: SurveyMoodService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.surveyService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ISurveyMood[]>) => res.ok),
                    map((res: HttpResponse<ISurveyMood[]>) => res.body)
                )
                .subscribe((res: ISurveyMood[]) => (this.surveys = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.surveyService
            .query()
            .pipe(
                filter((res: HttpResponse<ISurveyMood[]>) => res.ok),
                map((res: HttpResponse<ISurveyMood[]>) => res.body)
            )
            .subscribe(
                (res: ISurveyMood[]) => {
                    this.surveys = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSurveys();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISurveyMood) {
        return item.id;
    }

    registerChangeInSurveys() {
        this.eventSubscriber = this.eventManager.subscribe('surveyListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

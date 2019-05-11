import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISearchQueryMood } from 'app/shared/model/search-query-mood.model';
import { AccountService } from 'app/core';
import { SearchQueryMoodService } from './search-query-mood.service';

@Component({
    selector: 'jhi-search-query-mood',
    templateUrl: './search-query-mood.component.html'
})
export class SearchQueryMoodComponent implements OnInit, OnDestroy {
    searchQueries: ISearchQueryMood[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected searchQueryService: SearchQueryMoodService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.searchQueryService
            .query()
            .pipe(
                filter((res: HttpResponse<ISearchQueryMood[]>) => res.ok),
                map((res: HttpResponse<ISearchQueryMood[]>) => res.body)
            )
            .subscribe(
                (res: ISearchQueryMood[]) => {
                    this.searchQueries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSearchQueries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISearchQueryMood) {
        return item.id;
    }

    registerChangeInSearchQueries() {
        this.eventSubscriber = this.eventManager.subscribe('searchQueryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

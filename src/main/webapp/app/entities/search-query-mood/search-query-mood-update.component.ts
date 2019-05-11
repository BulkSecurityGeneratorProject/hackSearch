import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISearchQueryMood } from 'app/shared/model/search-query-mood.model';
import { SearchQueryMoodService } from './search-query-mood.service';

@Component({
    selector: 'jhi-search-query-mood-update',
    templateUrl: './search-query-mood-update.component.html'
})
export class SearchQueryMoodUpdateComponent implements OnInit {
    searchQuery: ISearchQueryMood;
    isSaving: boolean;
    createdAt: string;

    constructor(protected searchQueryService: SearchQueryMoodService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ searchQuery }) => {
            this.searchQuery = searchQuery;
            this.createdAt = this.searchQuery.createdAt != null ? this.searchQuery.createdAt.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.searchQuery.createdAt = this.createdAt != null ? moment(this.createdAt, DATE_TIME_FORMAT) : null;
        if (this.searchQuery.id !== undefined) {
            this.subscribeToSaveResponse(this.searchQueryService.update(this.searchQuery));
        } else {
            this.subscribeToSaveResponse(this.searchQueryService.create(this.searchQuery));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISearchQueryMood>>) {
        result.subscribe((res: HttpResponse<ISearchQueryMood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

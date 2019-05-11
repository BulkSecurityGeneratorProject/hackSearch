import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISearchQueryMood } from 'app/shared/model/search-query-mood.model';

@Component({
    selector: 'jhi-search-query-mood-detail',
    templateUrl: './search-query-mood-detail.component.html'
})
export class SearchQueryMoodDetailComponent implements OnInit {
    searchQuery: ISearchQueryMood;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ searchQuery }) => {
            this.searchQuery = searchQuery;
        });
    }

    previousState() {
        window.history.back();
    }
}

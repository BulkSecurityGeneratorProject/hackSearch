import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISearchQueryMood } from 'app/shared/model/search-query-mood.model';
import { SearchQueryMoodService } from './search-query-mood.service';

@Component({
    selector: 'jhi-search-query-mood-delete-dialog',
    templateUrl: './search-query-mood-delete-dialog.component.html'
})
export class SearchQueryMoodDeleteDialogComponent {
    searchQuery: ISearchQueryMood;

    constructor(
        protected searchQueryService: SearchQueryMoodService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.searchQueryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'searchQueryListModification',
                content: 'Deleted an searchQuery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-search-query-mood-delete-popup',
    template: ''
})
export class SearchQueryMoodDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ searchQuery }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SearchQueryMoodDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.searchQuery = searchQuery;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/search-query-mood', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/search-query-mood', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

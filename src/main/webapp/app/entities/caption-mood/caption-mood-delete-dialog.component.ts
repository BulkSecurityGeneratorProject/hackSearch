import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaptionMood } from 'app/shared/model/caption-mood.model';
import { CaptionMoodService } from './caption-mood.service';

@Component({
    selector: 'jhi-caption-mood-delete-dialog',
    templateUrl: './caption-mood-delete-dialog.component.html'
})
export class CaptionMoodDeleteDialogComponent {
    caption: ICaptionMood;

    constructor(
        protected captionService: CaptionMoodService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.captionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'captionListModification',
                content: 'Deleted an caption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-caption-mood-delete-popup',
    template: ''
})
export class CaptionMoodDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caption }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CaptionMoodDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.caption = caption;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/caption-mood', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/caption-mood', { outlets: { popup: null } }]);
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

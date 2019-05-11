import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';
import { TranslationLineMoodService } from './translation-line-mood.service';

@Component({
    selector: 'jhi-translation-line-mood-delete-dialog',
    templateUrl: './translation-line-mood-delete-dialog.component.html'
})
export class TranslationLineMoodDeleteDialogComponent {
    translationLine: ITranslationLineMood;

    constructor(
        protected translationLineService: TranslationLineMoodService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.translationLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'translationLineListModification',
                content: 'Deleted an translationLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-translation-line-mood-delete-popup',
    template: ''
})
export class TranslationLineMoodDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ translationLine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TranslationLineMoodDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.translationLine = translationLine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/translation-line-mood', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/translation-line-mood', { outlets: { popup: null } }]);
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

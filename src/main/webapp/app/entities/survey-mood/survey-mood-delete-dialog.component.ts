import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurveyMood } from 'app/shared/model/survey-mood.model';
import { SurveyMoodService } from './survey-mood.service';

@Component({
    selector: 'jhi-survey-mood-delete-dialog',
    templateUrl: './survey-mood-delete-dialog.component.html'
})
export class SurveyMoodDeleteDialogComponent {
    survey: ISurveyMood;

    constructor(protected surveyService: SurveyMoodService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.surveyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'surveyListModification',
                content: 'Deleted an survey'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-survey-mood-delete-popup',
    template: ''
})
export class SurveyMoodDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ survey }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SurveyMoodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.survey = survey;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/survey-mood', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/survey-mood', { outlets: { popup: null } }]);
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

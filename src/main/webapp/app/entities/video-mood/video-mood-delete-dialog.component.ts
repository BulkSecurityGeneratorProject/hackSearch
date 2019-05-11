import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideoMood } from 'app/shared/model/video-mood.model';
import { VideoMoodService } from './video-mood.service';

@Component({
    selector: 'jhi-video-mood-delete-dialog',
    templateUrl: './video-mood-delete-dialog.component.html'
})
export class VideoMoodDeleteDialogComponent {
    video: IVideoMood;

    constructor(protected videoService: VideoMoodService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.videoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'videoListModification',
                content: 'Deleted an video'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-video-mood-delete-popup',
    template: ''
})
export class VideoMoodDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ video }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VideoMoodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.video = video;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/video-mood', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/video-mood', { outlets: { popup: null } }]);
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

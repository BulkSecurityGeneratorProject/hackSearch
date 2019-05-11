import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICaptionMood } from 'app/shared/model/caption-mood.model';
import { CaptionMoodService } from './caption-mood.service';
import { IVideoMood } from 'app/shared/model/video-mood.model';
import { VideoMoodService } from 'app/entities/video-mood';

@Component({
    selector: 'jhi-caption-mood-update',
    templateUrl: './caption-mood-update.component.html'
})
export class CaptionMoodUpdateComponent implements OnInit {
    caption: ICaptionMood;
    isSaving: boolean;

    videos: IVideoMood[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected captionService: CaptionMoodService,
        protected videoService: VideoMoodService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caption }) => {
            this.caption = caption;
        });
        this.videoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVideoMood[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVideoMood[]>) => response.body)
            )
            .subscribe((res: IVideoMood[]) => (this.videos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.caption.id !== undefined) {
            this.subscribeToSaveResponse(this.captionService.update(this.caption));
        } else {
            this.subscribeToSaveResponse(this.captionService.create(this.caption));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaptionMood>>) {
        result.subscribe((res: HttpResponse<ICaptionMood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVideoById(index: number, item: IVideoMood) {
        return item.id;
    }
}

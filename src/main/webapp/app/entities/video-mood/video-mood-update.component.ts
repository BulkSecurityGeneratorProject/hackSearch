import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVideoMood } from 'app/shared/model/video-mood.model';
import { VideoMoodService } from './video-mood.service';

@Component({
    selector: 'jhi-video-mood-update',
    templateUrl: './video-mood-update.component.html'
})
export class VideoMoodUpdateComponent implements OnInit {
    video: IVideoMood;
    isSaving: boolean;

    constructor(protected videoService: VideoMoodService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ video }) => {
            this.video = video;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.video.id !== undefined) {
            this.subscribeToSaveResponse(this.videoService.update(this.video));
        } else {
            this.subscribeToSaveResponse(this.videoService.create(this.video));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoMood>>) {
        result.subscribe((res: HttpResponse<IVideoMood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVideoMood } from 'app/shared/model/video-mood.model';

@Component({
    selector: 'jhi-video-mood-detail',
    templateUrl: './video-mood-detail.component.html'
})
export class VideoMoodDetailComponent implements OnInit {
    video: IVideoMood;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ video }) => {
            this.video = video;
        });
    }

    previousState() {
        window.history.back();
    }
}

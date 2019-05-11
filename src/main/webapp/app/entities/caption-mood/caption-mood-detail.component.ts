import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaptionMood } from 'app/shared/model/caption-mood.model';

@Component({
    selector: 'jhi-caption-mood-detail',
    templateUrl: './caption-mood-detail.component.html'
})
export class CaptionMoodDetailComponent implements OnInit {
    caption: ICaptionMood;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caption }) => {
            this.caption = caption;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurveyMood } from 'app/shared/model/survey-mood.model';

@Component({
    selector: 'jhi-survey-mood-detail',
    templateUrl: './survey-mood-detail.component.html'
})
export class SurveyMoodDetailComponent implements OnInit {
    survey: ISurveyMood;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ survey }) => {
            this.survey = survey;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISurveyMood } from 'app/shared/model/survey-mood.model';
import { SurveyMoodService } from './survey-mood.service';

@Component({
    selector: 'jhi-survey-mood-update',
    templateUrl: './survey-mood-update.component.html'
})
export class SurveyMoodUpdateComponent implements OnInit {
    survey: ISurveyMood;
    isSaving: boolean;

    constructor(protected surveyService: SurveyMoodService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ survey }) => {
            this.survey = survey;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.survey.id !== undefined) {
            this.subscribeToSaveResponse(this.surveyService.update(this.survey));
        } else {
            this.subscribeToSaveResponse(this.surveyService.create(this.survey));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISurveyMood>>) {
        result.subscribe((res: HttpResponse<ISurveyMood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

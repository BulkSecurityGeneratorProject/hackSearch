import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { HackSearchSharedModule } from 'app/shared';
import {
    SurveyMoodComponent,
    SurveyMoodDetailComponent,
    SurveyMoodUpdateComponent,
    SurveyMoodDeletePopupComponent,
    SurveyMoodDeleteDialogComponent,
    surveyRoute,
    surveyPopupRoute
} from './';

const ENTITY_STATES = [...surveyRoute, ...surveyPopupRoute];

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SurveyMoodComponent,
        SurveyMoodDetailComponent,
        SurveyMoodUpdateComponent,
        SurveyMoodDeleteDialogComponent,
        SurveyMoodDeletePopupComponent
    ],
    entryComponents: [SurveyMoodComponent, SurveyMoodUpdateComponent, SurveyMoodDeleteDialogComponent, SurveyMoodDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchSurveyMoodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

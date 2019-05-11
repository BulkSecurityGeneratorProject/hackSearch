import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { HackSearchSharedModule } from 'app/shared';
import {
    SearchQueryMoodComponent,
    SearchQueryMoodDetailComponent,
    SearchQueryMoodUpdateComponent,
    SearchQueryMoodDeletePopupComponent,
    SearchQueryMoodDeleteDialogComponent,
    searchQueryRoute,
    searchQueryPopupRoute
} from './';

const ENTITY_STATES = [...searchQueryRoute, ...searchQueryPopupRoute];

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SearchQueryMoodComponent,
        SearchQueryMoodDetailComponent,
        SearchQueryMoodUpdateComponent,
        SearchQueryMoodDeleteDialogComponent,
        SearchQueryMoodDeletePopupComponent
    ],
    entryComponents: [
        SearchQueryMoodComponent,
        SearchQueryMoodUpdateComponent,
        SearchQueryMoodDeleteDialogComponent,
        SearchQueryMoodDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchSearchQueryMoodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

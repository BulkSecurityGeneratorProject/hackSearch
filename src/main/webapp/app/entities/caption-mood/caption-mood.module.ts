import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { HackSearchSharedModule } from 'app/shared';
import {
    CaptionMoodComponent,
    CaptionMoodDetailComponent,
    CaptionMoodUpdateComponent,
    CaptionMoodDeletePopupComponent,
    CaptionMoodDeleteDialogComponent,
    captionRoute,
    captionPopupRoute
} from './';

const ENTITY_STATES = [...captionRoute, ...captionPopupRoute];

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CaptionMoodComponent,
        CaptionMoodDetailComponent,
        CaptionMoodUpdateComponent,
        CaptionMoodDeleteDialogComponent,
        CaptionMoodDeletePopupComponent
    ],
    entryComponents: [CaptionMoodComponent, CaptionMoodUpdateComponent, CaptionMoodDeleteDialogComponent, CaptionMoodDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchCaptionMoodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

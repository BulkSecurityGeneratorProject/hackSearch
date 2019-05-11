import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { HackSearchSharedModule } from 'app/shared';
import {
    VideoMoodComponent,
    VideoMoodDetailComponent,
    VideoMoodUpdateComponent,
    VideoMoodDeletePopupComponent,
    VideoMoodDeleteDialogComponent,
    videoRoute,
    videoPopupRoute
} from './';

const ENTITY_STATES = [...videoRoute, ...videoPopupRoute];

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VideoMoodComponent,
        VideoMoodDetailComponent,
        VideoMoodUpdateComponent,
        VideoMoodDeleteDialogComponent,
        VideoMoodDeletePopupComponent
    ],
    entryComponents: [VideoMoodComponent, VideoMoodUpdateComponent, VideoMoodDeleteDialogComponent, VideoMoodDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchVideoMoodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { HackSearchSharedModule } from 'app/shared';
import {
    TranslationLineMoodComponent,
    TranslationLineMoodDetailComponent,
    TranslationLineMoodUpdateComponent,
    TranslationLineMoodDeletePopupComponent,
    TranslationLineMoodDeleteDialogComponent,
    translationLineRoute,
    translationLinePopupRoute
} from './';

const ENTITY_STATES = [...translationLineRoute, ...translationLinePopupRoute];

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TranslationLineMoodComponent,
        TranslationLineMoodDetailComponent,
        TranslationLineMoodUpdateComponent,
        TranslationLineMoodDeleteDialogComponent,
        TranslationLineMoodDeletePopupComponent
    ],
    entryComponents: [
        TranslationLineMoodComponent,
        TranslationLineMoodUpdateComponent,
        TranslationLineMoodDeleteDialogComponent,
        TranslationLineMoodDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchTranslationLineMoodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

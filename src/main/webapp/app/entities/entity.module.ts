import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'survey-mood',
                loadChildren: './survey-mood/survey-mood.module#HackSearchSurveyMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'caption-mood',
                loadChildren: './caption-mood/caption-mood.module#HackSearchCaptionMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'caption-mood',
                loadChildren: './caption-mood/caption-mood.module#HackSearchCaptionMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'caption-mood',
                loadChildren: './caption-mood/caption-mood.module#HackSearchCaptionMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'translation-line-mood',
                loadChildren: './translation-line-mood/translation-line-mood.module#HackSearchTranslationLineMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'caption-mood',
                loadChildren: './caption-mood/caption-mood.module#HackSearchCaptionMoodModule'
            },
            {
                path: 'video-mood',
                loadChildren: './video-mood/video-mood.module#HackSearchVideoMoodModule'
            },
            {
                path: 'search-query-mood',
                loadChildren: './search-query-mood/search-query-mood.module#HackSearchSearchQueryMoodModule'
            },
            {
                path: 'search-query-mood',
                loadChildren: './search-query-mood/search-query-mood.module#HackSearchSearchQueryMoodModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchEntityModule {}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';

@Component({
    selector: 'jhi-translation-line-mood-detail',
    templateUrl: './translation-line-mood-detail.component.html'
})
export class TranslationLineMoodDetailComponent implements OnInit {
    translationLine: ITranslationLineMood;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ translationLine }) => {
            this.translationLine = translationLine;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';
import { TranslationLineMoodService } from './translation-line-mood.service';
import { ICaptionMood } from 'app/shared/model/caption-mood.model';
import { CaptionMoodService } from 'app/entities/caption-mood';

@Component({
    selector: 'jhi-translation-line-mood-update',
    templateUrl: './translation-line-mood-update.component.html'
})
export class TranslationLineMoodUpdateComponent implements OnInit {
    translationLine: ITranslationLineMood;
    isSaving: boolean;

    captions: ICaptionMood[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected translationLineService: TranslationLineMoodService,
        protected captionService: CaptionMoodService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ translationLine }) => {
            this.translationLine = translationLine;
        });
        this.captionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICaptionMood[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICaptionMood[]>) => response.body)
            )
            .subscribe((res: ICaptionMood[]) => (this.captions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.translationLine.id !== undefined) {
            this.subscribeToSaveResponse(this.translationLineService.update(this.translationLine));
        } else {
            this.subscribeToSaveResponse(this.translationLineService.create(this.translationLine));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITranslationLineMood>>) {
        result.subscribe((res: HttpResponse<ITranslationLineMood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCaptionById(index: number, item: ICaptionMood) {
        return item.id;
    }
}

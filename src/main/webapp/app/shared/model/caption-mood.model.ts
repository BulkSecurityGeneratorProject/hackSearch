import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';

export interface ICaptionMood {
    id?: number;
    captionId?: string;
    trackKind?: string;
    language?: string;
    translationLines?: ITranslationLineMood[];
    videoIdId?: number;
}

export class CaptionMood implements ICaptionMood {
    constructor(
        public id?: number,
        public captionId?: string,
        public trackKind?: string,
        public language?: string,
        public translationLines?: ITranslationLineMood[],
        public videoIdId?: number
    ) {}
}

export interface ITranslationLineMood {
    id?: number;
    sndId?: number;
    text?: string;
    timeStart?: string;
    timeEnd?: string;
    episode?: number;
    captionIdId?: number;
}

export class TranslationLineMood implements ITranslationLineMood {
    constructor(
        public id?: number,
        public sndId?: number,
        public text?: string,
        public timeStart?: string,
        public timeEnd?: string,
        public episode?: number,
        public captionIdId?: number
    ) {}
}

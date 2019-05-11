import { ICaptionMood } from 'app/shared/model/caption-mood.model';

export interface IVideoMood {
    id?: number;
    videoId?: string;
    title?: string;
    episode?: number;
    soundcloud?: string;
    captionIds?: ICaptionMood[];
}

export class VideoMood implements IVideoMood {
    constructor(
        public id?: number,
        public videoId?: string,
        public title?: string,
        public episode?: number,
        public soundcloud?: string,
        public captionIds?: ICaptionMood[]
    ) {}
}

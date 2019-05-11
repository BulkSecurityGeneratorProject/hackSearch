import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVideoMood } from 'app/shared/model/video-mood.model';

type EntityResponseType = HttpResponse<String>;
type EntityArrayResponseType = HttpResponse<IVideoMood[]>;

@Injectable({ providedIn: 'root' })
export class HiThereService {
    public resourceUrl = SERVER_API_URL + 'api/extended/videos';

    constructor(protected http: HttpClient) {}

    checkVideos(): Observable<EntityResponseType> {
        return this.http.get<String>(this.resourceUrl + '/checkVideos', { observe: 'response' });
    }

    checkNewCaptions(): Observable<EntityResponseType> {
        return this.http.get<String>(this.resourceUrl + '/checkNewCaptions', { observe: 'response' });
    }

    checkNewCaptionLines(): Observable<EntityResponseType> {
        return this.http.get<String>(this.resourceUrl + '/checkNewCaptionLines', { observe: 'response' });
    }
    getYouTubeService(): Observable<EntityResponseType> {
        return this.http.get<String>(this.resourceUrl + '/getYouTubeService', { observe: 'response' });
    }
    setEpisodeinTranslation(): Observable<EntityResponseType> {
        return this.http.get<String>(this.resourceUrl + '/setEpisodeInTranslations', { observe: 'response' });
    }
    checkAll(): Observable<EntityResponseType> {
        return this.http.get<String>(this.resourceUrl + '/checkAll', { observe: 'response' });
    }
}

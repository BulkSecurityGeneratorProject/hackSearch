import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';
import { IVideoMood } from 'app/shared/model/video-mood.model';

type EntityResponseType = HttpResponse<ITranslationLineMood>;
type EntityArrayResponseType = HttpResponse<ITranslationLineMood[]>;

@Injectable({ providedIn: 'root' })
export class HomeService {
    public resourceUrl = SERVER_API_URL + 'api/translation-lines';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/translation-lines';
    public ressourceVideoExtendedUrl = SERVER_API_URL + 'api/extended/videos/videosOrderEpisode';

    constructor(protected http: HttpClient) {}

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITranslationLineMood[]>(this.resourceUrl + '/searchText', { params: options, observe: 'response' });
    }

    searchCount(req?: any): Observable<HttpResponse<number>> {
        const options = createRequestOption(req);
        return this.http.get<number>(this.resourceUrl + '/searchTextCount', { params: options, observe: 'response' });
    }

    getVideos(): Observable<HttpResponse<IVideoMood[]>> {
        return this.http.get<IVideoMood[]>(this.ressourceVideoExtendedUrl, { observe: 'response' });
    }
}

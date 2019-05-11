import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVideoMood } from 'app/shared/model/video-mood.model';

type EntityResponseType = HttpResponse<IVideoMood>;
type EntityArrayResponseType = HttpResponse<IVideoMood[]>;

@Injectable({ providedIn: 'root' })
export class VideoMoodService {
    public resourceUrl = SERVER_API_URL + 'api/videos';

    constructor(protected http: HttpClient) {}

    create(video: IVideoMood): Observable<EntityResponseType> {
        return this.http.post<IVideoMood>(this.resourceUrl, video, { observe: 'response' });
    }

    update(video: IVideoMood): Observable<EntityResponseType> {
        return this.http.put<IVideoMood>(this.resourceUrl, video, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVideoMood>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVideoMood[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

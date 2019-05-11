import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaptionMood } from 'app/shared/model/caption-mood.model';

type EntityResponseType = HttpResponse<ICaptionMood>;
type EntityArrayResponseType = HttpResponse<ICaptionMood[]>;

@Injectable({ providedIn: 'root' })
export class CaptionMoodService {
    public resourceUrl = SERVER_API_URL + 'api/captions';

    constructor(protected http: HttpClient) {}

    create(caption: ICaptionMood): Observable<EntityResponseType> {
        return this.http.post<ICaptionMood>(this.resourceUrl, caption, { observe: 'response' });
    }

    update(caption: ICaptionMood): Observable<EntityResponseType> {
        return this.http.put<ICaptionMood>(this.resourceUrl, caption, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICaptionMood>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICaptionMood[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

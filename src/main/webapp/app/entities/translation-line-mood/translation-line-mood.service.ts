import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITranslationLineMood } from 'app/shared/model/translation-line-mood.model';

type EntityResponseType = HttpResponse<ITranslationLineMood>;
type EntityArrayResponseType = HttpResponse<ITranslationLineMood[]>;

@Injectable({ providedIn: 'root' })
export class TranslationLineMoodService {
    public resourceUrl = SERVER_API_URL + 'api/translation-lines';

    constructor(protected http: HttpClient) {}

    create(translationLine: ITranslationLineMood): Observable<EntityResponseType> {
        return this.http.post<ITranslationLineMood>(this.resourceUrl, translationLine, { observe: 'response' });
    }

    update(translationLine: ITranslationLineMood): Observable<EntityResponseType> {
        return this.http.put<ITranslationLineMood>(this.resourceUrl, translationLine, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITranslationLineMood>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITranslationLineMood[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

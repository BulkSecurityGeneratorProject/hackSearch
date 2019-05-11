import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISurveyMood } from 'app/shared/model/survey-mood.model';

type EntityResponseType = HttpResponse<ISurveyMood>;
type EntityArrayResponseType = HttpResponse<ISurveyMood[]>;

@Injectable({ providedIn: 'root' })
export class SurveyMoodService {
    public resourceUrl = SERVER_API_URL + 'api/surveys';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/surveys';

    constructor(protected http: HttpClient) {}

    create(survey: ISurveyMood): Observable<EntityResponseType> {
        return this.http.post<ISurveyMood>(this.resourceUrl, survey, { observe: 'response' });
    }

    update(survey: ISurveyMood): Observable<EntityResponseType> {
        return this.http.put<ISurveyMood>(this.resourceUrl, survey, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISurveyMood>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISurveyMood[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISurveyMood[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

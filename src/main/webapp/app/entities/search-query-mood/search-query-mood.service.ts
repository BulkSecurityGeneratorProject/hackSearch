import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISearchQueryMood } from 'app/shared/model/search-query-mood.model';

type EntityResponseType = HttpResponse<ISearchQueryMood>;
type EntityArrayResponseType = HttpResponse<ISearchQueryMood[]>;

@Injectable({ providedIn: 'root' })
export class SearchQueryMoodService {
    public resourceUrl = SERVER_API_URL + 'api/search-queries';

    constructor(protected http: HttpClient) {}

    create(searchQuery: ISearchQueryMood): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(searchQuery);
        return this.http
            .post<ISearchQueryMood>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(searchQuery: ISearchQueryMood): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(searchQuery);
        return this.http
            .put<ISearchQueryMood>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISearchQueryMood>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISearchQueryMood[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(searchQuery: ISearchQueryMood): ISearchQueryMood {
        const copy: ISearchQueryMood = Object.assign({}, searchQuery, {
            createdAt: searchQuery.createdAt != null && searchQuery.createdAt.isValid() ? searchQuery.createdAt.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((searchQuery: ISearchQueryMood) => {
                searchQuery.createdAt = searchQuery.createdAt != null ? moment(searchQuery.createdAt) : null;
            });
        }
        return res;
    }
}

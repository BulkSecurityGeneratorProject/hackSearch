import { Moment } from 'moment';

export interface ISearchQueryMood {
    id?: number;
    query?: string;
    episode?: number;
    createdAt?: Moment;
}

export class SearchQueryMood implements ISearchQueryMood {
    constructor(public id?: number, public query?: string, public episode?: number, public createdAt?: Moment) {}
}

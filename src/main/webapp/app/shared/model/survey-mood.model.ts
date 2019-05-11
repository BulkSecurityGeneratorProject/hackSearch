export interface ISurveyMood {
    id?: number;
    text?: string;
    timeStart?: string;
    timeEnd?: string;
    episode?: number;
}

export class SurveyMood implements ISurveyMood {
    constructor(public id?: number, public text?: string, public timeStart?: string, public timeEnd?: string, public episode?: number) {}
}

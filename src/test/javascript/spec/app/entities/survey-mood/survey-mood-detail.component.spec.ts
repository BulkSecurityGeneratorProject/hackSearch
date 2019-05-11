/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { SurveyMoodDetailComponent } from 'app/entities/survey-mood/survey-mood-detail.component';
import { SurveyMood } from 'app/shared/model/survey-mood.model';

describe('Component Tests', () => {
    describe('SurveyMood Management Detail Component', () => {
        let comp: SurveyMoodDetailComponent;
        let fixture: ComponentFixture<SurveyMoodDetailComponent>;
        const route = ({ data: of({ survey: new SurveyMood(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SurveyMoodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SurveyMoodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurveyMoodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.survey).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

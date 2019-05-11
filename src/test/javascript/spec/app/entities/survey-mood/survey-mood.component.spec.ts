/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HackSearchTestModule } from '../../../test.module';
import { SurveyMoodComponent } from 'app/entities/survey-mood/survey-mood.component';
import { SurveyMoodService } from 'app/entities/survey-mood/survey-mood.service';
import { SurveyMood } from 'app/shared/model/survey-mood.model';

describe('Component Tests', () => {
    describe('SurveyMood Management Component', () => {
        let comp: SurveyMoodComponent;
        let fixture: ComponentFixture<SurveyMoodComponent>;
        let service: SurveyMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SurveyMoodComponent],
                providers: []
            })
                .overrideTemplate(SurveyMoodComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurveyMoodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveyMoodService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SurveyMood(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.surveys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

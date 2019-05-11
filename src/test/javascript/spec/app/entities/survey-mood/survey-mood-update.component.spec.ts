/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { SurveyMoodUpdateComponent } from 'app/entities/survey-mood/survey-mood-update.component';
import { SurveyMoodService } from 'app/entities/survey-mood/survey-mood.service';
import { SurveyMood } from 'app/shared/model/survey-mood.model';

describe('Component Tests', () => {
    describe('SurveyMood Management Update Component', () => {
        let comp: SurveyMoodUpdateComponent;
        let fixture: ComponentFixture<SurveyMoodUpdateComponent>;
        let service: SurveyMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SurveyMoodUpdateComponent]
            })
                .overrideTemplate(SurveyMoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurveyMoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveyMoodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SurveyMood(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.survey = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SurveyMood();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.survey = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

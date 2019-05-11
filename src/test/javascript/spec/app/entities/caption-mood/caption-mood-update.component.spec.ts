/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { CaptionMoodUpdateComponent } from 'app/entities/caption-mood/caption-mood-update.component';
import { CaptionMoodService } from 'app/entities/caption-mood/caption-mood.service';
import { CaptionMood } from 'app/shared/model/caption-mood.model';

describe('Component Tests', () => {
    describe('CaptionMood Management Update Component', () => {
        let comp: CaptionMoodUpdateComponent;
        let fixture: ComponentFixture<CaptionMoodUpdateComponent>;
        let service: CaptionMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [CaptionMoodUpdateComponent]
            })
                .overrideTemplate(CaptionMoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaptionMoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaptionMoodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CaptionMood(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.caption = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CaptionMood();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.caption = entity;
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

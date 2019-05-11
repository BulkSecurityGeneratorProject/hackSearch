/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { TranslationLineMoodUpdateComponent } from 'app/entities/translation-line-mood/translation-line-mood-update.component';
import { TranslationLineMoodService } from 'app/entities/translation-line-mood/translation-line-mood.service';
import { TranslationLineMood } from 'app/shared/model/translation-line-mood.model';

describe('Component Tests', () => {
    describe('TranslationLineMood Management Update Component', () => {
        let comp: TranslationLineMoodUpdateComponent;
        let fixture: ComponentFixture<TranslationLineMoodUpdateComponent>;
        let service: TranslationLineMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [TranslationLineMoodUpdateComponent]
            })
                .overrideTemplate(TranslationLineMoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TranslationLineMoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationLineMoodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TranslationLineMood(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.translationLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TranslationLineMood();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.translationLine = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { SearchQueryMoodUpdateComponent } from 'app/entities/search-query-mood/search-query-mood-update.component';
import { SearchQueryMoodService } from 'app/entities/search-query-mood/search-query-mood.service';
import { SearchQueryMood } from 'app/shared/model/search-query-mood.model';

describe('Component Tests', () => {
    describe('SearchQueryMood Management Update Component', () => {
        let comp: SearchQueryMoodUpdateComponent;
        let fixture: ComponentFixture<SearchQueryMoodUpdateComponent>;
        let service: SearchQueryMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SearchQueryMoodUpdateComponent]
            })
                .overrideTemplate(SearchQueryMoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SearchQueryMoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchQueryMoodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SearchQueryMood(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.searchQuery = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SearchQueryMood();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.searchQuery = entity;
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

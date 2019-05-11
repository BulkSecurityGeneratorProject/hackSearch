/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HackSearchTestModule } from '../../../test.module';
import { SearchQueryMoodComponent } from 'app/entities/search-query-mood/search-query-mood.component';
import { SearchQueryMoodService } from 'app/entities/search-query-mood/search-query-mood.service';
import { SearchQueryMood } from 'app/shared/model/search-query-mood.model';

describe('Component Tests', () => {
    describe('SearchQueryMood Management Component', () => {
        let comp: SearchQueryMoodComponent;
        let fixture: ComponentFixture<SearchQueryMoodComponent>;
        let service: SearchQueryMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SearchQueryMoodComponent],
                providers: []
            })
                .overrideTemplate(SearchQueryMoodComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SearchQueryMoodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchQueryMoodService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SearchQueryMood(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.searchQueries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

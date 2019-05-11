/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { SearchQueryMoodDetailComponent } from 'app/entities/search-query-mood/search-query-mood-detail.component';
import { SearchQueryMood } from 'app/shared/model/search-query-mood.model';

describe('Component Tests', () => {
    describe('SearchQueryMood Management Detail Component', () => {
        let comp: SearchQueryMoodDetailComponent;
        let fixture: ComponentFixture<SearchQueryMoodDetailComponent>;
        const route = ({ data: of({ searchQuery: new SearchQueryMood(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SearchQueryMoodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SearchQueryMoodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SearchQueryMoodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.searchQuery).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

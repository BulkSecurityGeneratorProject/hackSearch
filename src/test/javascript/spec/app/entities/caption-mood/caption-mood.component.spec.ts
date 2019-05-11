/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HackSearchTestModule } from '../../../test.module';
import { CaptionMoodComponent } from 'app/entities/caption-mood/caption-mood.component';
import { CaptionMoodService } from 'app/entities/caption-mood/caption-mood.service';
import { CaptionMood } from 'app/shared/model/caption-mood.model';

describe('Component Tests', () => {
    describe('CaptionMood Management Component', () => {
        let comp: CaptionMoodComponent;
        let fixture: ComponentFixture<CaptionMoodComponent>;
        let service: CaptionMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [CaptionMoodComponent],
                providers: []
            })
                .overrideTemplate(CaptionMoodComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaptionMoodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaptionMoodService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CaptionMood(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.captions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

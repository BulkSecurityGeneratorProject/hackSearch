/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { CaptionMoodDetailComponent } from 'app/entities/caption-mood/caption-mood-detail.component';
import { CaptionMood } from 'app/shared/model/caption-mood.model';

describe('Component Tests', () => {
    describe('CaptionMood Management Detail Component', () => {
        let comp: CaptionMoodDetailComponent;
        let fixture: ComponentFixture<CaptionMoodDetailComponent>;
        const route = ({ data: of({ caption: new CaptionMood(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [CaptionMoodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CaptionMoodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaptionMoodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.caption).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

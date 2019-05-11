/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { VideoMoodDetailComponent } from 'app/entities/video-mood/video-mood-detail.component';
import { VideoMood } from 'app/shared/model/video-mood.model';

describe('Component Tests', () => {
    describe('VideoMood Management Detail Component', () => {
        let comp: VideoMoodDetailComponent;
        let fixture: ComponentFixture<VideoMoodDetailComponent>;
        const route = ({ data: of({ video: new VideoMood(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [VideoMoodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VideoMoodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VideoMoodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.video).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

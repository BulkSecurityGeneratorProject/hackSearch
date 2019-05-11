/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HackSearchTestModule } from '../../../test.module';
import { VideoMoodComponent } from 'app/entities/video-mood/video-mood.component';
import { VideoMoodService } from 'app/entities/video-mood/video-mood.service';
import { VideoMood } from 'app/shared/model/video-mood.model';

describe('Component Tests', () => {
    describe('VideoMood Management Component', () => {
        let comp: VideoMoodComponent;
        let fixture: ComponentFixture<VideoMoodComponent>;
        let service: VideoMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [VideoMoodComponent],
                providers: []
            })
                .overrideTemplate(VideoMoodComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VideoMoodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoMoodService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VideoMood(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.videos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

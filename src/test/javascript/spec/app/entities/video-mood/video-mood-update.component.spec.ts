/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { VideoMoodUpdateComponent } from 'app/entities/video-mood/video-mood-update.component';
import { VideoMoodService } from 'app/entities/video-mood/video-mood.service';
import { VideoMood } from 'app/shared/model/video-mood.model';

describe('Component Tests', () => {
    describe('VideoMood Management Update Component', () => {
        let comp: VideoMoodUpdateComponent;
        let fixture: ComponentFixture<VideoMoodUpdateComponent>;
        let service: VideoMoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [VideoMoodUpdateComponent]
            })
                .overrideTemplate(VideoMoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VideoMoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoMoodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new VideoMood(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.video = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new VideoMood();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.video = entity;
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

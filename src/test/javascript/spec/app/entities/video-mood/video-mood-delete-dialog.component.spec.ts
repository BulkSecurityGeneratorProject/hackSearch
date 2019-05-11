/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HackSearchTestModule } from '../../../test.module';
import { VideoMoodDeleteDialogComponent } from 'app/entities/video-mood/video-mood-delete-dialog.component';
import { VideoMoodService } from 'app/entities/video-mood/video-mood.service';

describe('Component Tests', () => {
    describe('VideoMood Management Delete Component', () => {
        let comp: VideoMoodDeleteDialogComponent;
        let fixture: ComponentFixture<VideoMoodDeleteDialogComponent>;
        let service: VideoMoodService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [VideoMoodDeleteDialogComponent]
            })
                .overrideTemplate(VideoMoodDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VideoMoodDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoMoodService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

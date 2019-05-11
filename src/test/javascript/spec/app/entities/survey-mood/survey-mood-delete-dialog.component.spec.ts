/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HackSearchTestModule } from '../../../test.module';
import { SurveyMoodDeleteDialogComponent } from 'app/entities/survey-mood/survey-mood-delete-dialog.component';
import { SurveyMoodService } from 'app/entities/survey-mood/survey-mood.service';

describe('Component Tests', () => {
    describe('SurveyMood Management Delete Component', () => {
        let comp: SurveyMoodDeleteDialogComponent;
        let fixture: ComponentFixture<SurveyMoodDeleteDialogComponent>;
        let service: SurveyMoodService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SurveyMoodDeleteDialogComponent]
            })
                .overrideTemplate(SurveyMoodDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurveyMoodDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveyMoodService);
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

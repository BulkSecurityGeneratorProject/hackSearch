/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HackSearchTestModule } from '../../../test.module';
import { TranslationLineMoodDeleteDialogComponent } from 'app/entities/translation-line-mood/translation-line-mood-delete-dialog.component';
import { TranslationLineMoodService } from 'app/entities/translation-line-mood/translation-line-mood.service';

describe('Component Tests', () => {
    describe('TranslationLineMood Management Delete Component', () => {
        let comp: TranslationLineMoodDeleteDialogComponent;
        let fixture: ComponentFixture<TranslationLineMoodDeleteDialogComponent>;
        let service: TranslationLineMoodService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [TranslationLineMoodDeleteDialogComponent]
            })
                .overrideTemplate(TranslationLineMoodDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TranslationLineMoodDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranslationLineMoodService);
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

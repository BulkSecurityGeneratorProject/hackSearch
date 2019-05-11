/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HackSearchTestModule } from '../../../test.module';
import { SearchQueryMoodDeleteDialogComponent } from 'app/entities/search-query-mood/search-query-mood-delete-dialog.component';
import { SearchQueryMoodService } from 'app/entities/search-query-mood/search-query-mood.service';

describe('Component Tests', () => {
    describe('SearchQueryMood Management Delete Component', () => {
        let comp: SearchQueryMoodDeleteDialogComponent;
        let fixture: ComponentFixture<SearchQueryMoodDeleteDialogComponent>;
        let service: SearchQueryMoodService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [SearchQueryMoodDeleteDialogComponent]
            })
                .overrideTemplate(SearchQueryMoodDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SearchQueryMoodDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchQueryMoodService);
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

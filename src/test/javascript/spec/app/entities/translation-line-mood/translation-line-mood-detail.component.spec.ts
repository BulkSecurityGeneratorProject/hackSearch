/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HackSearchTestModule } from '../../../test.module';
import { TranslationLineMoodDetailComponent } from 'app/entities/translation-line-mood/translation-line-mood-detail.component';
import { TranslationLineMood } from 'app/shared/model/translation-line-mood.model';

describe('Component Tests', () => {
    describe('TranslationLineMood Management Detail Component', () => {
        let comp: TranslationLineMoodDetailComponent;
        let fixture: ComponentFixture<TranslationLineMoodDetailComponent>;
        const route = ({ data: of({ translationLine: new TranslationLineMood(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HackSearchTestModule],
                declarations: [TranslationLineMoodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TranslationLineMoodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TranslationLineMoodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.translationLine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

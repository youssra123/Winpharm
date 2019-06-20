/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { LaboratoireDetailComponent } from 'app/entities/laboratoire/laboratoire-detail.component';
import { Laboratoire } from 'app/shared/model/laboratoire.model';

describe('Component Tests', () => {
  describe('Laboratoire Management Detail Component', () => {
    let comp: LaboratoireDetailComponent;
    let fixture: ComponentFixture<LaboratoireDetailComponent>;
    const route = ({ data: of({ laboratoire: new Laboratoire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [LaboratoireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LaboratoireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LaboratoireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.laboratoire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

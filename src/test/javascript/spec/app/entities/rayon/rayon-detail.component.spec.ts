/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { RayonDetailComponent } from 'app/entities/rayon/rayon-detail.component';
import { Rayon } from 'app/shared/model/rayon.model';

describe('Component Tests', () => {
  describe('Rayon Management Detail Component', () => {
    let comp: RayonDetailComponent;
    let fixture: ComponentFixture<RayonDetailComponent>;
    const route = ({ data: of({ rayon: new Rayon(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [RayonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RayonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RayonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rayon).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

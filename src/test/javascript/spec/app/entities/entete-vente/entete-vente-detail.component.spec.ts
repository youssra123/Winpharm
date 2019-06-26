/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { EnteteVenteDetailComponent } from 'app/entities/entete-vente/entete-vente-detail.component';
import { EnteteVente } from 'app/shared/model/entete-vente.model';

describe('Component Tests', () => {
  describe('EnteteVente Management Detail Component', () => {
    let comp: EnteteVenteDetailComponent;
    let fixture: ComponentFixture<EnteteVenteDetailComponent>;
    const route = ({ data: of({ enteteVente: new EnteteVente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [EnteteVenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnteteVenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnteteVenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enteteVente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

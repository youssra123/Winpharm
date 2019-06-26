/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { LigneVenteDetailComponent } from 'app/entities/ligne-vente/ligne-vente-detail.component';
import { LigneVente } from 'app/shared/model/ligne-vente.model';

describe('Component Tests', () => {
  describe('LigneVente Management Detail Component', () => {
    let comp: LigneVenteDetailComponent;
    let fixture: ComponentFixture<LigneVenteDetailComponent>;
    const route = ({ data: of({ ligneVente: new LigneVente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [LigneVenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LigneVenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LigneVenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ligneVente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

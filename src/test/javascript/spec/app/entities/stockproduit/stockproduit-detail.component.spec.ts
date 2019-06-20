/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { StockproduitDetailComponent } from 'app/entities/stockproduit/stockproduit-detail.component';
import { Stockproduit } from 'app/shared/model/stockproduit.model';

describe('Component Tests', () => {
  describe('Stockproduit Management Detail Component', () => {
    let comp: StockproduitDetailComponent;
    let fixture: ComponentFixture<StockproduitDetailComponent>;
    const route = ({ data: of({ stockproduit: new Stockproduit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [StockproduitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockproduitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockproduitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockproduit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

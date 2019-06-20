/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { FammilleTarifaireDetailComponent } from 'app/entities/fammille-tarifaire/fammille-tarifaire-detail.component';
import { FammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';

describe('Component Tests', () => {
  describe('FammilleTarifaire Management Detail Component', () => {
    let comp: FammilleTarifaireDetailComponent;
    let fixture: ComponentFixture<FammilleTarifaireDetailComponent>;
    const route = ({ data: of({ fammilleTarifaire: new FammilleTarifaire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [FammilleTarifaireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FammilleTarifaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FammilleTarifaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fammilleTarifaire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

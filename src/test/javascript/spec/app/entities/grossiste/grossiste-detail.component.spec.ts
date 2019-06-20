/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { GrossisteDetailComponent } from 'app/entities/grossiste/grossiste-detail.component';
import { Grossiste } from 'app/shared/model/grossiste.model';

describe('Component Tests', () => {
  describe('Grossiste Management Detail Component', () => {
    let comp: GrossisteDetailComponent;
    let fixture: ComponentFixture<GrossisteDetailComponent>;
    const route = ({ data: of({ grossiste: new Grossiste(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [GrossisteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GrossisteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrossisteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grossiste).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

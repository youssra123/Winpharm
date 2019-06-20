/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { FormeDetailComponent } from 'app/entities/forme/forme-detail.component';
import { Forme } from 'app/shared/model/forme.model';

describe('Component Tests', () => {
  describe('Forme Management Detail Component', () => {
    let comp: FormeDetailComponent;
    let fixture: ComponentFixture<FormeDetailComponent>;
    const route = ({ data: of({ forme: new Forme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [FormeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.forme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

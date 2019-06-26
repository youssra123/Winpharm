/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { EnteteVenteUpdateComponent } from 'app/entities/entete-vente/entete-vente-update.component';
import { EnteteVenteService } from 'app/entities/entete-vente/entete-vente.service';
import { EnteteVente } from 'app/shared/model/entete-vente.model';

describe('Component Tests', () => {
  describe('EnteteVente Management Update Component', () => {
    let comp: EnteteVenteUpdateComponent;
    let fixture: ComponentFixture<EnteteVenteUpdateComponent>;
    let service: EnteteVenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [EnteteVenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnteteVenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnteteVenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnteteVenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EnteteVente(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EnteteVente();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

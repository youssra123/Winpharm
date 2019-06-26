/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { LigneVenteUpdateComponent } from 'app/entities/ligne-vente/ligne-vente-update.component';
import { LigneVenteService } from 'app/entities/ligne-vente/ligne-vente.service';
import { LigneVente } from 'app/shared/model/ligne-vente.model';

describe('Component Tests', () => {
  describe('LigneVente Management Update Component', () => {
    let comp: LigneVenteUpdateComponent;
    let fixture: ComponentFixture<LigneVenteUpdateComponent>;
    let service: LigneVenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [LigneVenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LigneVenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LigneVenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LigneVenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LigneVente(123);
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
        const entity = new LigneVente();
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { LaboratoireUpdateComponent } from 'app/entities/laboratoire/laboratoire-update.component';
import { LaboratoireService } from 'app/entities/laboratoire/laboratoire.service';
import { Laboratoire } from 'app/shared/model/laboratoire.model';

describe('Component Tests', () => {
  describe('Laboratoire Management Update Component', () => {
    let comp: LaboratoireUpdateComponent;
    let fixture: ComponentFixture<LaboratoireUpdateComponent>;
    let service: LaboratoireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [LaboratoireUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LaboratoireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LaboratoireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LaboratoireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Laboratoire(123);
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
        const entity = new Laboratoire();
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

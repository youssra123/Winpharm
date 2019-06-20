/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { RayonUpdateComponent } from 'app/entities/rayon/rayon-update.component';
import { RayonService } from 'app/entities/rayon/rayon.service';
import { Rayon } from 'app/shared/model/rayon.model';

describe('Component Tests', () => {
  describe('Rayon Management Update Component', () => {
    let comp: RayonUpdateComponent;
    let fixture: ComponentFixture<RayonUpdateComponent>;
    let service: RayonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [RayonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RayonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RayonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RayonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Rayon(123);
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
        const entity = new Rayon();
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

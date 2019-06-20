/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { GrossisteUpdateComponent } from 'app/entities/grossiste/grossiste-update.component';
import { GrossisteService } from 'app/entities/grossiste/grossiste.service';
import { Grossiste } from 'app/shared/model/grossiste.model';

describe('Component Tests', () => {
  describe('Grossiste Management Update Component', () => {
    let comp: GrossisteUpdateComponent;
    let fixture: ComponentFixture<GrossisteUpdateComponent>;
    let service: GrossisteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [GrossisteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GrossisteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrossisteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrossisteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Grossiste(123);
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
        const entity = new Grossiste();
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

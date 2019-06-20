/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { FormeUpdateComponent } from 'app/entities/forme/forme-update.component';
import { FormeService } from 'app/entities/forme/forme.service';
import { Forme } from 'app/shared/model/forme.model';

describe('Component Tests', () => {
  describe('Forme Management Update Component', () => {
    let comp: FormeUpdateComponent;
    let fixture: ComponentFixture<FormeUpdateComponent>;
    let service: FormeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [FormeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Forme(123);
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
        const entity = new Forme();
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

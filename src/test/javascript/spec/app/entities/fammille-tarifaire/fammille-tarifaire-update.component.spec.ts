/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { FammilleTarifaireUpdateComponent } from 'app/entities/fammille-tarifaire/fammille-tarifaire-update.component';
import { FammilleTarifaireService } from 'app/entities/fammille-tarifaire/fammille-tarifaire.service';
import { FammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';

describe('Component Tests', () => {
  describe('FammilleTarifaire Management Update Component', () => {
    let comp: FammilleTarifaireUpdateComponent;
    let fixture: ComponentFixture<FammilleTarifaireUpdateComponent>;
    let service: FammilleTarifaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [FammilleTarifaireUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FammilleTarifaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FammilleTarifaireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FammilleTarifaireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FammilleTarifaire(123);
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
        const entity = new FammilleTarifaire();
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

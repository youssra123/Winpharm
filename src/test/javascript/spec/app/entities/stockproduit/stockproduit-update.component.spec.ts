/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { StockproduitUpdateComponent } from 'app/entities/stockproduit/stockproduit-update.component';
import { StockproduitService } from 'app/entities/stockproduit/stockproduit.service';
import { Stockproduit } from 'app/shared/model/stockproduit.model';

describe('Component Tests', () => {
  describe('Stockproduit Management Update Component', () => {
    let comp: StockproduitUpdateComponent;
    let fixture: ComponentFixture<StockproduitUpdateComponent>;
    let service: StockproduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [StockproduitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockproduitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockproduitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockproduitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Stockproduit(123);
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
        const entity = new Stockproduit();
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { WinpharmTestModule } from '../../../test.module';
import { ClientUpdateComponent } from 'app/entities/client/client-update.component';
import { ClientService } from 'app/entities/client/client.service';
import { Client } from 'app/shared/model/client.model';

describe('Component Tests', () => {
  describe('Client Management Update Component', () => {
    let comp: ClientUpdateComponent;
    let fixture: ComponentFixture<ClientUpdateComponent>;
    let service: ClientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [ClientUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Client(123);
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
        const entity = new Client();
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { GrossisteDeleteDialogComponent } from 'app/entities/grossiste/grossiste-delete-dialog.component';
import { GrossisteService } from 'app/entities/grossiste/grossiste.service';

describe('Component Tests', () => {
  describe('Grossiste Management Delete Component', () => {
    let comp: GrossisteDeleteDialogComponent;
    let fixture: ComponentFixture<GrossisteDeleteDialogComponent>;
    let service: GrossisteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [GrossisteDeleteDialogComponent]
      })
        .overrideTemplate(GrossisteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrossisteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrossisteService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

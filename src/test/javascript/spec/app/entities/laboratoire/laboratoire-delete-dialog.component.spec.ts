/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { LaboratoireDeleteDialogComponent } from 'app/entities/laboratoire/laboratoire-delete-dialog.component';
import { LaboratoireService } from 'app/entities/laboratoire/laboratoire.service';

describe('Component Tests', () => {
  describe('Laboratoire Management Delete Component', () => {
    let comp: LaboratoireDeleteDialogComponent;
    let fixture: ComponentFixture<LaboratoireDeleteDialogComponent>;
    let service: LaboratoireService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [LaboratoireDeleteDialogComponent]
      })
        .overrideTemplate(LaboratoireDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LaboratoireDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LaboratoireService);
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { FormeDeleteDialogComponent } from 'app/entities/forme/forme-delete-dialog.component';
import { FormeService } from 'app/entities/forme/forme.service';

describe('Component Tests', () => {
  describe('Forme Management Delete Component', () => {
    let comp: FormeDeleteDialogComponent;
    let fixture: ComponentFixture<FormeDeleteDialogComponent>;
    let service: FormeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [FormeDeleteDialogComponent]
      })
        .overrideTemplate(FormeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormeService);
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

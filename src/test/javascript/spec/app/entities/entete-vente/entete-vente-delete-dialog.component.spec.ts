/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { EnteteVenteDeleteDialogComponent } from 'app/entities/entete-vente/entete-vente-delete-dialog.component';
import { EnteteVenteService } from 'app/entities/entete-vente/entete-vente.service';

describe('Component Tests', () => {
  describe('EnteteVente Management Delete Component', () => {
    let comp: EnteteVenteDeleteDialogComponent;
    let fixture: ComponentFixture<EnteteVenteDeleteDialogComponent>;
    let service: EnteteVenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [EnteteVenteDeleteDialogComponent]
      })
        .overrideTemplate(EnteteVenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnteteVenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnteteVenteService);
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

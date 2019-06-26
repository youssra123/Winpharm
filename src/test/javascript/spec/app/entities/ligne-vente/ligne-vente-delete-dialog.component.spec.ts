/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { LigneVenteDeleteDialogComponent } from 'app/entities/ligne-vente/ligne-vente-delete-dialog.component';
import { LigneVenteService } from 'app/entities/ligne-vente/ligne-vente.service';

describe('Component Tests', () => {
  describe('LigneVente Management Delete Component', () => {
    let comp: LigneVenteDeleteDialogComponent;
    let fixture: ComponentFixture<LigneVenteDeleteDialogComponent>;
    let service: LigneVenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [LigneVenteDeleteDialogComponent]
      })
        .overrideTemplate(LigneVenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LigneVenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LigneVenteService);
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { ProduitDeleteDialogComponent } from 'app/entities/produit/produit-delete-dialog.component';
import { ProduitService } from 'app/entities/produit/produit.service';

describe('Component Tests', () => {
  describe('Produit Management Delete Component', () => {
    let comp: ProduitDeleteDialogComponent;
    let fixture: ComponentFixture<ProduitDeleteDialogComponent>;
    let service: ProduitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [ProduitDeleteDialogComponent]
      })
        .overrideTemplate(ProduitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProduitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitService);
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

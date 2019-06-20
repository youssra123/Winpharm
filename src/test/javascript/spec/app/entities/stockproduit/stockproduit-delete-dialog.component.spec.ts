/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { StockproduitDeleteDialogComponent } from 'app/entities/stockproduit/stockproduit-delete-dialog.component';
import { StockproduitService } from 'app/entities/stockproduit/stockproduit.service';

describe('Component Tests', () => {
  describe('Stockproduit Management Delete Component', () => {
    let comp: StockproduitDeleteDialogComponent;
    let fixture: ComponentFixture<StockproduitDeleteDialogComponent>;
    let service: StockproduitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [StockproduitDeleteDialogComponent]
      })
        .overrideTemplate(StockproduitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockproduitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockproduitService);
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WinpharmTestModule } from '../../../test.module';
import { FammilleTarifaireDeleteDialogComponent } from 'app/entities/fammille-tarifaire/fammille-tarifaire-delete-dialog.component';
import { FammilleTarifaireService } from 'app/entities/fammille-tarifaire/fammille-tarifaire.service';

describe('Component Tests', () => {
  describe('FammilleTarifaire Management Delete Component', () => {
    let comp: FammilleTarifaireDeleteDialogComponent;
    let fixture: ComponentFixture<FammilleTarifaireDeleteDialogComponent>;
    let service: FammilleTarifaireService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WinpharmTestModule],
        declarations: [FammilleTarifaireDeleteDialogComponent]
      })
        .overrideTemplate(FammilleTarifaireDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FammilleTarifaireDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FammilleTarifaireService);
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

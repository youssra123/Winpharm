import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILaboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from './laboratoire.service';

@Component({
  selector: 'jhi-laboratoire-delete-dialog',
  templateUrl: './laboratoire-delete-dialog.component.html'
})
export class LaboratoireDeleteDialogComponent {
  laboratoire: ILaboratoire;

  constructor(
    protected laboratoireService: LaboratoireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.laboratoireService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'laboratoireListModification',
        content: 'Deleted an laboratoire'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-laboratoire-delete-popup',
  template: ''
})
export class LaboratoireDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ laboratoire }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LaboratoireDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.laboratoire = laboratoire;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/laboratoire', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/laboratoire', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

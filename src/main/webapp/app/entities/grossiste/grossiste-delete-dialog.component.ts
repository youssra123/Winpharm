import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrossiste } from 'app/shared/model/grossiste.model';
import { GrossisteService } from './grossiste.service';

@Component({
  selector: 'jhi-grossiste-delete-dialog',
  templateUrl: './grossiste-delete-dialog.component.html'
})
export class GrossisteDeleteDialogComponent {
  grossiste: IGrossiste;

  constructor(protected grossisteService: GrossisteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.grossisteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'grossisteListModification',
        content: 'Deleted an grossiste'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-grossiste-delete-popup',
  template: ''
})
export class GrossisteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grossiste }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GrossisteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.grossiste = grossiste;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/grossiste', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/grossiste', { outlets: { popup: null } }]);
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

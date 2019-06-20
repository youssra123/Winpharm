import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IForme } from 'app/shared/model/forme.model';
import { FormeService } from './forme.service';

@Component({
  selector: 'jhi-forme-delete-dialog',
  templateUrl: './forme-delete-dialog.component.html'
})
export class FormeDeleteDialogComponent {
  forme: IForme;

  constructor(protected formeService: FormeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.formeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'formeListModification',
        content: 'Deleted an forme'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-forme-delete-popup',
  template: ''
})
export class FormeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ forme }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FormeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.forme = forme;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/forme', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/forme', { outlets: { popup: null } }]);
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

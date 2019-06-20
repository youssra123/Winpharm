import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRayon } from 'app/shared/model/rayon.model';
import { RayonService } from './rayon.service';

@Component({
  selector: 'jhi-rayon-delete-dialog',
  templateUrl: './rayon-delete-dialog.component.html'
})
export class RayonDeleteDialogComponent {
  rayon: IRayon;

  constructor(protected rayonService: RayonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rayonService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rayonListModification',
        content: 'Deleted an rayon'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-rayon-delete-popup',
  template: ''
})
export class RayonDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rayon }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RayonDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.rayon = rayon;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/rayon', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/rayon', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnteteVente } from 'app/shared/model/entete-vente.model';
import { EnteteVenteService } from './entete-vente.service';

@Component({
  selector: 'jhi-entete-vente-delete-dialog',
  templateUrl: './entete-vente-delete-dialog.component.html'
})
export class EnteteVenteDeleteDialogComponent {
  enteteVente: IEnteteVente;

  constructor(
    protected enteteVenteService: EnteteVenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.enteteVenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'enteteVenteListModification',
        content: 'Deleted an enteteVente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-entete-vente-delete-popup',
  template: ''
})
export class EnteteVenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ enteteVente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EnteteVenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.enteteVente = enteteVente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/entete-vente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/entete-vente', { outlets: { popup: null } }]);
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

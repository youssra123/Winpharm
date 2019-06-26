import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILigneVente } from 'app/shared/model/ligne-vente.model';
import { LigneVenteService } from './ligne-vente.service';

@Component({
  selector: 'jhi-ligne-vente-delete-dialog',
  templateUrl: './ligne-vente-delete-dialog.component.html'
})
export class LigneVenteDeleteDialogComponent {
  ligneVente: ILigneVente;

  constructor(
    protected ligneVenteService: LigneVenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ligneVenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ligneVenteListModification',
        content: 'Deleted an ligneVente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ligne-vente-delete-popup',
  template: ''
})
export class LigneVenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ligneVente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LigneVenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ligneVente = ligneVente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ligne-vente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ligne-vente', { outlets: { popup: null } }]);
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

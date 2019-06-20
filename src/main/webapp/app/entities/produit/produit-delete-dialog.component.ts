import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';

@Component({
  selector: 'jhi-produit-delete-dialog',
  templateUrl: './produit-delete-dialog.component.html'
})
export class ProduitDeleteDialogComponent {
  produit: IProduit;

  constructor(protected produitService: ProduitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.produitService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'produitListModification',
        content: 'Deleted an produit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-produit-delete-popup',
  template: ''
})
export class ProduitDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProduitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.produit = produit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/produit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/produit', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockproduit } from 'app/shared/model/stockproduit.model';
import { StockproduitService } from './stockproduit.service';

@Component({
  selector: 'jhi-stockproduit-delete-dialog',
  templateUrl: './stockproduit-delete-dialog.component.html'
})
export class StockproduitDeleteDialogComponent {
  stockproduit: IStockproduit;

  constructor(
    protected stockproduitService: StockproduitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockproduitService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockproduitListModification',
        content: 'Deleted an stockproduit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stockproduit-delete-popup',
  template: ''
})
export class StockproduitDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockproduit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockproduitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stockproduit = stockproduit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stockproduit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stockproduit', { outlets: { popup: null } }]);
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

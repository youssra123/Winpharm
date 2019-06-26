import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';
import { FammilleTarifaireService } from './fammille-tarifaire.service';

@Component({
  selector: 'jhi-fammille-tarifaire-delete-dialog',
  templateUrl: './fammille-tarifaire-delete-dialog.component.html',
  styleUrls: ['famille.scss']
})
export class FammilleTarifaireDeleteDialogComponent {
  fammilleTarifaire: IFammilleTarifaire;

  constructor(
    protected fammilleTarifaireService: FammilleTarifaireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fammilleTarifaireService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fammilleTarifaireListModification',
        content: 'Deleted an fammilleTarifaire'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fammille-tarifaire-delete-popup',
  template: ''
})
export class FammilleTarifaireDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fammilleTarifaire }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FammilleTarifaireDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fammilleTarifaire = fammilleTarifaire;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fammille-tarifaire', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fammille-tarifaire', { outlets: { popup: null } }]);
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

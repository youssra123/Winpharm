import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ILigneVente } from 'app/shared/model/ligne-vente.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { LigneVenteService } from './ligne-vente.service';
@Component({
  selector: 'jhi-ligne-vente2-detail',
  templateUrl: './ligne-vente2-detail.component.html',
  styles: [
    `
      .bottom {
        margin-bottom: 4%;
        padding-left: 8%;
      }
      .text-content {
        font-weight: bold;
        font-size: 18px;
      }
    `
  ]
})
export class LigneVente2DetailComponent implements OnInit, OnDestroy {
  ligneVente: ILigneVente;
  currentAccount: any;
  ligneVentes: ILigneVente[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected ligneVenteService: LigneVenteService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,

    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.ligneVentes = [];
    this.ligneVentes = [
      {
        id: 2,
        ligneVenteDesignation: 'tel',
        ligneVentePrixHT: 0,
        ligneVentePrixTTC: 0,
        ligneVenteQte: 0,
        ligneVenteTotalHT: 0,
        ligneVenteTotalTTC: 0,
        produit: {
          id: 0,
          produitActif: 'string',
          produitCodeBarre: 0,
          produitDosage: 0,
          produitLibelle: 'string',
          produitUniDosage: 'string',
          produitUniVolume: 'string',
          produitVolume: 0,
          produit_categorie: {
            categorieLibelle: 'string',
            id: 0
          }
        }
      },
      {
        id: 2,
        ligneVenteDesignation: 'tel2',
        ligneVentePrixHT: 0,
        ligneVentePrixTTC: 0,
        ligneVenteQte: 0,
        ligneVenteTotalHT: 0,
        ligneVenteTotalTTC: 0,
        produit: {
          id: 0,
          produitActif: 'string',
          produitCodeBarre: 0,
          produitDosage: 0,
          produitLibelle: 'string',
          produitUniDosage: 'string',
          produitUniVolume: 'string',
          produitVolume: 0,
          produit_categorie: {
            categorieLibelle: 'string',
            id: 0
          }
        }
      }
    ];
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/ligne-vente'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/ligne-vente',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLigneVentes();
    this.activatedRoute.data.subscribe(({ ligneVente }) => {
      this.ligneVente = ligneVente;
    });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILigneVente) {
    return item.id;
  }

  registerChangeInLigneVentes() {
    this.eventSubscriber = this.eventManager.subscribe('ligneVenteListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLigneVentes(data: ILigneVente[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.ligneVentes = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
  previousState() {
    window.history.back();
  }
}

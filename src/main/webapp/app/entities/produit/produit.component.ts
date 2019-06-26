import { Component, OnInit, OnDestroy, ElementRef, HostListener, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IProduit } from 'app/shared/model/produit.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ProduitService } from './produit.service';
import { MdbTablePaginationComponent, MdbTableDirective } from 'angular-bootstrap-md';
@Component({
  selector: 'jhi-produit',
  templateUrl: './produit.component.html'
})
export class ProduitComponent implements OnInit, OnDestroy {
  produits: IProduit[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  libelle = '';
  mdbTable: MdbTableDirective;
  mdbTablePagination: MdbTablePaginationComponent;
  row: ElementRef;

  elements: any = [];
  headElements = ['id', 'first', 'last', 'handle'];

  searchText: string = '';
  previous: string;

  maxVisibleItems: number = 8;

  constructor(
    private cdRef: ChangeDetectorRef,
    protected produitService: ProduitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService
  ) {
    this.produits = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.produitService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProduit[]>) => this.paginateProduits(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }
  onKey(libelle: string) {
    this.produits = [];
    this.produitService
      .findByDes(libelle, {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProduit[]>) => this.paginateProduits(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  reset() {
    this.page = 0;
    this.produits = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProduits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProduit) {
    return item.id;
  }

  registerChangeInProduits() {
    this.eventSubscriber = this.eventManager.subscribe('produitListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProduits(data: IProduit[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.produits.push(data[i]);
    }
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

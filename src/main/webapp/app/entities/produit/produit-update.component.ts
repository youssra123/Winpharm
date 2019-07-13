import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { IRayon } from 'app/shared/model/rayon.model';
import { RayonService } from 'app/entities/rayon';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie';
import { IFammilleTarifaire } from 'app/shared/model/fammille-tarifaire.model';
import { FammilleTarifaireService } from 'app/entities/fammille-tarifaire';
import { ILaboratoire } from 'app/shared/model/laboratoire.model';
import { LaboratoireService } from 'app/entities/laboratoire';
import { IGrossiste } from 'app/shared/model/grossiste.model';
import { GrossisteService } from 'app/entities/grossiste';
import { IForme } from 'app/shared/model/forme.model';
import { FormeService } from 'app/entities/forme';
import { IStock, Stock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
  styleUrls: ['produit.scss']
})
export class ProduitUpdateComponent implements OnInit {
  isSaving: boolean;
  public isCollapsed = true;
  public count = 0;
  public isCollapsedd = true;
  public isCollapseddd = true;

  rayons: IRayon[];

  categories: ICategorie[];

  fammilletarifaires: IFammilleTarifaire[];

  laboratoires: ILaboratoire[];

  grossistes: IGrossiste[];

  formes: IForme[];

  stocks: IStock[];

  editForm = this.fb.group({
    id: [],
    produitLibelle: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
    produitActif: [null, [Validators.required]],
    produitCodeBarre: [],
    produitDosage: [],
    produitUniDosage: [],
    produitVolume: [],
    produitUniVolume: [],
    produit_rayon: [],
    produit_categorie: [null, Validators.required],
    produit_fam_tar: [null, Validators.required],
    produit_laboratoire: [],
    produit_grossiste: [],
    proform: [null, Validators.required],
    stock: []
  });

  public innerStock: IStock = {
    ...new Stock(),
    id: 100,
    stockCouvertureMax: 0,
    stockCouvertureMin: 0,
    stockDateCreation: undefined,
    stockDatePeremption1: undefined,
    stockDatePeremption2: undefined,
    stockDatePeremption3: undefined,
    stockPrix1: 0,
    stockPrix2: 0,
    stockPrix3: 0,
    stockPrixHT1: 0,
    stockPrixHT2: 0,
    stockPrixHT3: 0,
    stockQte1: 0,
    stockQte2: 0,
    stockQte3: 0,
    produit: undefined
  };
  public produitStock: IStock;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected produitService: ProduitService,
    protected rayonService: RayonService,
    protected categorieService: CategorieService,
    protected fammilleTarifaireService: FammilleTarifaireService,
    protected laboratoireService: LaboratoireService,
    protected grossisteService: GrossisteService,
    protected formeService: FormeService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  setInnerStock(data: IStock) {
    this.innerStock.stockCouvertureMin = data.stockCouvertureMin ? data.stockCouvertureMin : 0;
    this.innerStock.stockCouvertureMax = data.stockCouvertureMax ? data.stockCouvertureMax : 0;
    this.innerStock.stockDatePeremption1 = data.stockDatePeremption1 ? data.stockDatePeremption1 : undefined;
    this.innerStock.stockDatePeremption2 = data.stockDatePeremption2 ? data.stockDatePeremption2 : undefined;
    this.innerStock.stockDatePeremption3 = data.stockDatePeremption3 ? data.stockDatePeremption3 : undefined;
    this.innerStock.stockPrix1 = data.stockPrix1 ? data.stockPrix1 : 0;
    this.innerStock.stockPrix2 = data.stockPrix2 ? data.stockPrix2 : 0;
    this.innerStock.stockPrix3 = data.stockPrix3 ? data.stockPrix3 : 0;
    this.innerStock.stockPrixHT1 = data.stockPrixHT1 ? data.stockPrixHT1 : 0;
    this.innerStock.stockPrixHT2 = data.stockPrixHT2 ? data.stockPrixHT2 : 0;
    this.innerStock.stockPrixHT3 = data.stockPrixHT3 ? data.stockPrixHT3 : 0;
    this.innerStock.stockQte1 = data.stockQte1 ? data.stockQte1 : 0;
    this.innerStock.stockQte2 = data.stockQte2 ? data.stockQte2 : 0;
    this.innerStock.stockQte3 = data.stockQte3 ? data.stockQte3 : 0;
    this.innerStock.produit = data.produit ? data.produit : undefined;
    this.innerStock.stockDateCreation = moment(new Date(), DATE_TIME_FORMAT);
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);
    });
    this.rayonService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRayon[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRayon[]>) => response.body)
      )
      .subscribe((res: IRayon[]) => (this.rayons = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categorieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategorie[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategorie[]>) => response.body)
      )
      .subscribe((res: ICategorie[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.fammilleTarifaireService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFammilleTarifaire[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFammilleTarifaire[]>) => response.body)
      )
      .subscribe((res: IFammilleTarifaire[]) => (this.fammilletarifaires = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.laboratoireService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILaboratoire[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILaboratoire[]>) => response.body)
      )
      .subscribe((res: ILaboratoire[]) => (this.laboratoires = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.grossisteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGrossiste[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGrossiste[]>) => response.body)
      )
      .subscribe((res: IGrossiste[]) => (this.grossistes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.formeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IForme[]>) => mayBeOk.ok),
        map((response: HttpResponse<IForme[]>) => response.body)
      )
      .subscribe((res: IForme[]) => (this.formes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.stockService
      .query({ filter: 'produit-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IStock[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStock[]>) => response.body)
      )
      .subscribe(
        (res: IStock[]) => {
          if (!this.editForm.get('stock').value || !this.editForm.get('stock').value.id) {
            this.stocks = res;
          } else {
            this.stockService
              .find(this.editForm.get('stock').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IStock>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IStock>) => subResponse.body)
              )
              .subscribe(
                (subRes: IStock) => (this.stocks = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(produit: IProduit) {
    this.editForm.patchValue({
      id: produit.id,
      produitLibelle: produit.produitLibelle,
      produitActif: produit.produitActif,
      produitCodeBarre: produit.produitCodeBarre,
      produitDosage: produit.produitDosage,
      produitUniDosage: produit.produitUniDosage,
      produitVolume: produit.produitVolume,
      produitUniVolume: produit.produitUniVolume,
      produit_rayon: produit.produit_rayon,
      produit_categorie: produit.produit_categorie,
      produit_fam_tar: produit.produit_fam_tar,
      produit_laboratoire: produit.produit_laboratoire,
      produit_grossiste: produit.produit_grossiste,
      proform: produit.proform,
      stock: produit.stock
    });
    const stockFromProduit = {
      ...new Stock(),
      id: produit.stock.id,
      stockCouvertureMin: produit.stock.stockCouvertureMin,
      stockCouvertureMax: produit.stock.stockCouvertureMax,
      stockQte1: produit.stock.stockQte1,
      stockQte2: produit.stock.stockQte2,
      stockQte3: produit.stock.stockQte3,
      stockPrix1: produit.stock.stockPrix1,
      stockPrix2: produit.stock.stockPrix2,
      stockPrix3: produit.stock.stockPrix3,
      stockPrixHT1: produit.stock.stockPrixHT1,
      stockPrixHT2: produit.stock.stockPrixHT2,
      stockPrixHT3: produit.stock.stockPrixHT3,
      stockDatePeremption1: moment(produit.stock.stockDatePeremption1, DATE_TIME_FORMAT),
      stockDatePeremption2: moment(produit.stock.stockDatePeremption2, DATE_TIME_FORMAT),
      stockDatePeremption3: moment(produit.stock.stockDatePeremption3, DATE_TIME_FORMAT),
      stockDateCreation: moment(produit.stock.stockDateCreation, DATE_TIME_FORMAT)
    };
    this.produitStock = stockFromProduit;
    console.log('produit-stock : ');
    console.log(this.produitStock);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    const entity = {
      ...new Produit(),
      id: this.editForm.get(['id']).value,
      produitLibelle: this.editForm.get(['produitLibelle']).value,
      produitActif: this.editForm.get(['produitActif']).value,
      produitCodeBarre: this.editForm.get(['produitCodeBarre']).value,
      produitDosage: this.editForm.get(['produitDosage']).value,
      produitUniDosage: this.editForm.get(['produitUniDosage']).value,
      produitVolume: this.editForm.get(['produitVolume']).value,
      produitUniVolume: this.editForm.get(['produitUniVolume']).value,
      produit_rayon: this.editForm.get(['produit_rayon']).value,
      produit_categorie: this.editForm.get(['produit_categorie']).value,
      produit_fam_tar: this.editForm.get(['produit_fam_tar']).value,
      produit_laboratoire: this.editForm.get(['produit_laboratoire']).value,
      produit_grossiste: this.editForm.get(['produit_grossiste']).value,
      proform: this.editForm.get(['proform']).value,
      stock: this.innerStock
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>) {
    result.subscribe((res: HttpResponse<IProduit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRayonById(index: number, item: IRayon) {
    return item.id;
  }

  trackCategorieById(index: number, item: ICategorie) {
    return item.id;
  }

  trackFammilleTarifaireById(index: number, item: IFammilleTarifaire) {
    return item.id;
  }

  trackLaboratoireById(index: number, item: ILaboratoire) {
    return item.id;
  }

  trackGrossisteById(index: number, item: IGrossiste) {
    return item.id;
  }

  trackFormeById(index: number, item: IForme) {
    return item.id;
  }

  trackStockById(index: number, item: IStock) {
    return item.id;
  }
  Display() {
    this.count = this.count + 1;
    if (this.count < 4) {
      if (this.count === 1) {
        this.isCollapsed = false;
      }
      if (this.count === 2) {
        this.isCollapsedd = false;
      }
      if (this.count === 3) {
        this.isCollapseddd = false;
      }
    } else {
      if (this.count === 6) {
        this.isCollapsed = true;
      }
      if (this.count === 5) {
        this.isCollapsedd = true;
      }
      if (this.count === 4) {
        this.isCollapseddd = true;
      }
    }
  }
}

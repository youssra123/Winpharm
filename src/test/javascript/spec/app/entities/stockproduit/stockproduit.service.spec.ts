/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StockproduitService } from 'app/entities/stockproduit/stockproduit.service';
import { IStockproduit, Stockproduit } from 'app/shared/model/stockproduit.model';

describe('Service Tests', () => {
  describe('Stockproduit Service', () => {
    let injector: TestBed;
    let service: StockproduitService;
    let httpMock: HttpTestingController;
    let elemDefault: IStockproduit;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StockproduitService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Stockproduit(0, 0, currentDate, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            stockProduitDateCreation: currentDate.format(DATE_TIME_FORMAT),
            stockProduitDatePeremption: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Stockproduit', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            stockProduitDateCreation: currentDate.format(DATE_TIME_FORMAT),
            stockProduitDatePeremption: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            stockProduitDateCreation: currentDate,
            stockProduitDatePeremption: currentDate
          },
          returnedFromService
        );
        service
          .create(new Stockproduit(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Stockproduit', async () => {
        const returnedFromService = Object.assign(
          {
            stockProduitQuantite: 1,
            stockProduitDateCreation: currentDate.format(DATE_TIME_FORMAT),
            stockProduitDatePeremption: currentDate.format(DATE_TIME_FORMAT),
            stockProduitPrixVente: 1,
            stockProduitPrixHorsTaxe: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            stockProduitDateCreation: currentDate,
            stockProduitDatePeremption: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Stockproduit', async () => {
        const returnedFromService = Object.assign(
          {
            stockProduitQuantite: 1,
            stockProduitDateCreation: currentDate.format(DATE_TIME_FORMAT),
            stockProduitDatePeremption: currentDate.format(DATE_TIME_FORMAT),
            stockProduitPrixVente: 1,
            stockProduitPrixHorsTaxe: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            stockProduitDateCreation: currentDate,
            stockProduitDatePeremption: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Stockproduit', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

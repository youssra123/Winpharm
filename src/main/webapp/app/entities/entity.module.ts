import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ville',
        loadChildren: './ville/ville.module#WinpharmVilleModule'
      },
      {
        path: 'grossiste',
        loadChildren: './grossiste/grossiste.module#WinpharmGrossisteModule'
      },
      {
        path: 'forme',
        loadChildren: './forme/forme.module#WinpharmFormeModule'
      },
      {
        path: 'categorie',
        loadChildren: './categorie/categorie.module#WinpharmCategorieModule'
      },
      {
        path: 'rayon',
        loadChildren: './rayon/rayon.module#WinpharmRayonModule'
      },
      {
        path: 'fammille-tarifaire',
        loadChildren: './fammille-tarifaire/fammille-tarifaire.module#WinpharmFammilleTarifaireModule'
      },
      {
        path: 'laboratoire',
        loadChildren: './laboratoire/laboratoire.module#WinpharmLaboratoireModule'
      },
      {
        path: 'stock',
        loadChildren: './stock/stock.module#WinpharmStockModule'
      },
      {
        path: 'produit',
        loadChildren: './produit/produit.module#WinpharmProduitModule'
      },

      {
        path: 'client',
        loadChildren: './client/client.module#WinpharmClientModule'
      },
      {
        path: 'entete-vente',
        loadChildren: './entete-vente/entete-vente.module#WinpharmEnteteVenteModule'
      },
      {
        path: 'ligne-vente',
        loadChildren: './ligne-vente/ligne-vente.module#WinpharmLigneVenteModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WinpharmEntityModule {}

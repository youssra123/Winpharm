package com.sophatel.winpharm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Stockproduit.
 */
@Entity
@Table(name = "stockproduit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stockproduit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stock_produit_quantite", nullable = false)
    private Integer stockProduitQuantite;

    @NotNull
    @Column(name = "stock_produit_date_creation", nullable = false)
    private ZonedDateTime stockProduitDateCreation;

    @NotNull
    @Column(name = "stock_produit_date_peremption", nullable = false)
    private ZonedDateTime stockProduitDatePeremption;

    @NotNull
    @Column(name = "stock_produit_prix_vente", nullable = false)
    private Double stockProduitPrixVente;

    @NotNull
    @Column(name = "stock_produit_prix_hors_taxe", nullable = false)
    private Double stockProduitPrixHorsTaxe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockproduits")
    private Produit stock_produit_produit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockproduits")
    private Stock stock_produit_stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockProduitQuantite() {
        return stockProduitQuantite;
    }

    public Stockproduit stockProduitQuantite(Integer stockProduitQuantite) {
        this.stockProduitQuantite = stockProduitQuantite;
        return this;
    }

    public void setStockProduitQuantite(Integer stockProduitQuantite) {
        this.stockProduitQuantite = stockProduitQuantite;
    }

    public ZonedDateTime getStockProduitDateCreation() {
        return stockProduitDateCreation;
    }

    public Stockproduit stockProduitDateCreation(ZonedDateTime stockProduitDateCreation) {
        this.stockProduitDateCreation = stockProduitDateCreation;
        return this;
    }

    public void setStockProduitDateCreation(ZonedDateTime stockProduitDateCreation) {
        this.stockProduitDateCreation = stockProduitDateCreation;
    }

    public ZonedDateTime getStockProduitDatePeremption() {
        return stockProduitDatePeremption;
    }

    public Stockproduit stockProduitDatePeremption(ZonedDateTime stockProduitDatePeremption) {
        this.stockProduitDatePeremption = stockProduitDatePeremption;
        return this;
    }

    public void setStockProduitDatePeremption(ZonedDateTime stockProduitDatePeremption) {
        this.stockProduitDatePeremption = stockProduitDatePeremption;
    }

    public Double getStockProduitPrixVente() {
        return stockProduitPrixVente;
    }

    public Stockproduit stockProduitPrixVente(Double stockProduitPrixVente) {
        this.stockProduitPrixVente = stockProduitPrixVente;
        return this;
    }

    public void setStockProduitPrixVente(Double stockProduitPrixVente) {
        this.stockProduitPrixVente = stockProduitPrixVente;
    }

    public Double getStockProduitPrixHorsTaxe() {
        return stockProduitPrixHorsTaxe;
    }

    public Stockproduit stockProduitPrixHorsTaxe(Double stockProduitPrixHorsTaxe) {
        this.stockProduitPrixHorsTaxe = stockProduitPrixHorsTaxe;
        return this;
    }

    public void setStockProduitPrixHorsTaxe(Double stockProduitPrixHorsTaxe) {
        this.stockProduitPrixHorsTaxe = stockProduitPrixHorsTaxe;
    }

    public Produit getStock_produit_produit() {
        return stock_produit_produit;
    }

    public Stockproduit stock_produit_produit(Produit produit) {
        this.stock_produit_produit = produit;
        return this;
    }

    public void setStock_produit_produit(Produit produit) {
        this.stock_produit_produit = produit;
    }

    public Stock getStock_produit_stock() {
        return stock_produit_stock;
    }

    public Stockproduit stock_produit_stock(Stock stock) {
        this.stock_produit_stock = stock;
        return this;
    }

    public void setStock_produit_stock(Stock stock) {
        this.stock_produit_stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stockproduit)) {
            return false;
        }
        return id != null && id.equals(((Stockproduit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stockproduit{" +
            "id=" + getId() +
            ", stockProduitQuantite=" + getStockProduitQuantite() +
            ", stockProduitDateCreation='" + getStockProduitDateCreation() + "'" +
            ", stockProduitDatePeremption='" + getStockProduitDatePeremption() + "'" +
            ", stockProduitPrixVente=" + getStockProduitPrixVente() +
            ", stockProduitPrixHorsTaxe=" + getStockProduitPrixHorsTaxe() +
            "}";
    }
}

package com.sophatel.winpharm.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stock_couverture_min", nullable = false)
    private Integer stockCouvertureMin;

    @NotNull
    @Column(name = "stock_couverture_max", nullable = false)
    private Integer stockCouvertureMax;

    @NotNull
    @Min(value = 0)
    @Column(name = "stock_qte_1", nullable = false)
    private Integer stockQte1;

    @Min(value = 0)
    @Column(name = "stock_qte_2")
    private Integer stockQte2;

    @Min(value = 0)
    @Column(name = "stock_qte_3")
    private Integer stockQte3;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "stock_prix_1", nullable = false)
    private Double stockPrix1;

    @DecimalMin(value = "0")
    @Column(name = "stock_prix_2")
    private Double stockPrix2;

    @DecimalMin(value = "0")
    @Column(name = "stock_prix_3")
    private Double stockPrix3;

    @NotNull
    @Column(name = "stock_date_peremption_1", nullable = false)
    private ZonedDateTime stockDatePeremption1;

    @Column(name = "stock_date_peremption_2")
    private ZonedDateTime stockDatePeremption2;

    @Column(name = "stock_date_peremption_3")
    private ZonedDateTime stockDatePeremption3;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "stock_prix_ht_1", nullable = false)
    private Double stockPrixHT1;

    @DecimalMin(value = "0")
    @Column(name = "stock_prix_ht_2")
    private Double stockPrixHT2;

    @Column(name = "stock_prix_ht_3")
    private Double stockPrixHT3;

    @NotNull
    @Column(name = "stock_date_creation", nullable = false)
    private ZonedDateTime stockDateCreation;

    @OneToOne(mappedBy = "stock")
    @JsonIgnore
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockCouvertureMin() {
        return stockCouvertureMin;
    }

    public Stock stockCouvertureMin(Integer stockCouvertureMin) {
        this.stockCouvertureMin = stockCouvertureMin;
        return this;
    }

    public void setStockCouvertureMin(Integer stockCouvertureMin) {
        this.stockCouvertureMin = stockCouvertureMin;
    }

    public Integer getStockCouvertureMax() {
        return stockCouvertureMax;
    }

    public Stock stockCouvertureMax(Integer stockCouvertureMax) {
        this.stockCouvertureMax = stockCouvertureMax;
        return this;
    }

    public void setStockCouvertureMax(Integer stockCouvertureMax) {
        this.stockCouvertureMax = stockCouvertureMax;
    }

    public Integer getStockQte1() {
        return stockQte1;
    }

    public Stock stockQte1(Integer stockQte1) {
        this.stockQte1 = stockQte1;
        return this;
    }

    public void setStockQte1(Integer stockQte1) {
        this.stockQte1 = stockQte1;
    }

    public Integer getStockQte2() {
        return stockQte2;
    }

    public Stock stockQte2(Integer stockQte2) {
        this.stockQte2 = stockQte2;
        return this;
    }

    public void setStockQte2(Integer stockQte2) {
        this.stockQte2 = stockQte2;
    }

    public Integer getStockQte3() {
        return stockQte3;
    }

    public Stock stockQte3(Integer stockQte3) {
        this.stockQte3 = stockQte3;
        return this;
    }

    public void setStockQte3(Integer stockQte3) {
        this.stockQte3 = stockQte3;
    }

    public Double getStockPrix1() {
        return stockPrix1;
    }

    public Stock stockPrix1(Double stockPrix1) {
        this.stockPrix1 = stockPrix1;
        return this;
    }

    public void setStockPrix1(Double stockPrix1) {
        this.stockPrix1 = stockPrix1;
    }

    public Double getStockPrix2() {
        return stockPrix2;
    }

    public Stock stockPrix2(Double stockPrix2) {
        this.stockPrix2 = stockPrix2;
        return this;
    }

    public void setStockPrix2(Double stockPrix2) {
        this.stockPrix2 = stockPrix2;
    }

    public Double getStockPrix3() {
        return stockPrix3;
    }

    public Stock stockPrix3(Double stockPrix3) {
        this.stockPrix3 = stockPrix3;
        return this;
    }

    public void setStockPrix3(Double stockPrix3) {
        this.stockPrix3 = stockPrix3;
    }

    public ZonedDateTime getStockDatePeremption1() {
        return stockDatePeremption1;
    }

    public Stock stockDatePeremption1(ZonedDateTime stockDatePeremption1) {
        this.stockDatePeremption1 = stockDatePeremption1;
        return this;
    }

    public void setStockDatePeremption1(ZonedDateTime stockDatePeremption1) {
        this.stockDatePeremption1 = stockDatePeremption1;
    }

    public ZonedDateTime getStockDatePeremption2() {
        return stockDatePeremption2;
    }

    public Stock stockDatePeremption2(ZonedDateTime stockDatePeremption2) {
        this.stockDatePeremption2 = stockDatePeremption2;
        return this;
    }

    public void setStockDatePeremption2(ZonedDateTime stockDatePeremption2) {
        this.stockDatePeremption2 = stockDatePeremption2;
    }

    public ZonedDateTime getStockDatePeremption3() {
        return stockDatePeremption3;
    }

    public Stock stockDatePeremption3(ZonedDateTime stockDatePeremption3) {
        this.stockDatePeremption3 = stockDatePeremption3;
        return this;
    }

    public void setStockDatePeremption3(ZonedDateTime stockDatePeremption3) {
        this.stockDatePeremption3 = stockDatePeremption3;
    }

    public Double getStockPrixHT1() {
        return stockPrixHT1;
    }

    public Stock stockPrixHT1(Double stockPrixHT1) {
        this.stockPrixHT1 = stockPrixHT1;
        return this;
    }

    public void setStockPrixHT1(Double stockPrixHT1) {
        this.stockPrixHT1 = stockPrixHT1;
    }

    public Double getStockPrixHT2() {
        return stockPrixHT2;
    }

    public Stock stockPrixHT2(Double stockPrixHT2) {
        this.stockPrixHT2 = stockPrixHT2;
        return this;
    }

    public void setStockPrixHT2(Double stockPrixHT2) {
        this.stockPrixHT2 = stockPrixHT2;
    }

    public Double getStockPrixHT3() {
        return stockPrixHT3;
    }

    public Stock stockPrixHT3(Double stockPrixHT3) {
        this.stockPrixHT3 = stockPrixHT3;
        return this;
    }

    public void setStockPrixHT3(Double stockPrixHT3) {
        this.stockPrixHT3 = stockPrixHT3;
    }

    public ZonedDateTime getStockDateCreation() {
        return stockDateCreation;
    }

    public Stock stockDateCreation(ZonedDateTime stockDateCreation) {
        this.stockDateCreation = stockDateCreation;
        return this;
    }

    public void setStockDateCreation(ZonedDateTime stockDateCreation) {
        this.stockDateCreation = stockDateCreation;
    }

    public Produit getProduit() {
        return produit;
    }

    public Stock produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", stockCouvertureMin=" + getStockCouvertureMin() +
            ", stockCouvertureMax=" + getStockCouvertureMax() +
            ", stockQte1=" + getStockQte1() +
            ", stockQte2=" + getStockQte2() +
            ", stockQte3=" + getStockQte3() +
            ", stockPrix1=" + getStockPrix1() +
            ", stockPrix2=" + getStockPrix2() +
            ", stockPrix3=" + getStockPrix3() +
            ", stockDatePeremption1='" + getStockDatePeremption1() + "'" +
            ", stockDatePeremption2='" + getStockDatePeremption2() + "'" +
            ", stockDatePeremption3='" + getStockDatePeremption3() + "'" +
            ", stockPrixHT1=" + getStockPrixHT1() +
            ", stockPrixHT2=" + getStockPrixHT2() +
            ", stockPrixHT3=" + getStockPrixHT3() +
            ", stockDateCreation='" + getStockDateCreation() + "'" +
            "}";
    }

    public int stockActuel(){
        if (this.stockQte1 > 0 && this.stockDatePeremption1.compareTo(ZonedDateTime.now()) >= 0){
            return 1;
        }
        if (this.stockQte2 > 0 && this.stockDatePeremption2.compareTo(ZonedDateTime.now()) >= 0){
            return 2;
        }
        if (this.stockQte3 > 0 && this.stockDatePeremption3.compareTo(ZonedDateTime.now()) >= 0){
            return 3;
        }
        return 0;
    }
}

package com.sophatel.winpharm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

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
            "}";
    }
}

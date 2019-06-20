package com.sophatel.winpharm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A FammilleTarifaire.
 */
@Entity
@Table(name = "fammille_tarifaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FammilleTarifaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(name = "fami_tarif_libelle", length = 30, nullable = false)
    private String famiTarifLibelle;

    @NotNull
    @Column(name = "fami_tarif_forfait", nullable = false)
    private Double famiTarifForfait;

    @Column(name = "fami_tarif_code_tva")
    private Integer famiTarifCodeTVA;

    @Column(name = "fami_tarif_taux_tva")
    private Double famiTarifTauxTVA;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamiTarifLibelle() {
        return famiTarifLibelle;
    }

    public FammilleTarifaire famiTarifLibelle(String famiTarifLibelle) {
        this.famiTarifLibelle = famiTarifLibelle;
        return this;
    }

    public void setFamiTarifLibelle(String famiTarifLibelle) {
        this.famiTarifLibelle = famiTarifLibelle;
    }

    public Double getFamiTarifForfait() {
        return famiTarifForfait;
    }

    public FammilleTarifaire famiTarifForfait(Double famiTarifForfait) {
        this.famiTarifForfait = famiTarifForfait;
        return this;
    }

    public void setFamiTarifForfait(Double famiTarifForfait) {
        this.famiTarifForfait = famiTarifForfait;
    }

    public Integer getFamiTarifCodeTVA() {
        return famiTarifCodeTVA;
    }

    public FammilleTarifaire famiTarifCodeTVA(Integer famiTarifCodeTVA) {
        this.famiTarifCodeTVA = famiTarifCodeTVA;
        return this;
    }

    public void setFamiTarifCodeTVA(Integer famiTarifCodeTVA) {
        this.famiTarifCodeTVA = famiTarifCodeTVA;
    }

    public Double getFamiTarifTauxTVA() {
        return famiTarifTauxTVA;
    }

    public FammilleTarifaire famiTarifTauxTVA(Double famiTarifTauxTVA) {
        this.famiTarifTauxTVA = famiTarifTauxTVA;
        return this;
    }

    public void setFamiTarifTauxTVA(Double famiTarifTauxTVA) {
        this.famiTarifTauxTVA = famiTarifTauxTVA;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FammilleTarifaire)) {
            return false;
        }
        return id != null && id.equals(((FammilleTarifaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FammilleTarifaire{" +
            "id=" + getId() +
            ", famiTarifLibelle='" + getFamiTarifLibelle() + "'" +
            ", famiTarifForfait=" + getFamiTarifForfait() +
            ", famiTarifCodeTVA=" + getFamiTarifCodeTVA() +
            ", famiTarifTauxTVA=" + getFamiTarifTauxTVA() +
            "}";
    }
}

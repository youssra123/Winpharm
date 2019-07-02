package com.sophatel.winpharm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A LigneVente.
 */
@Entity
@Table(name = "ligne_vente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LigneVente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ligne_vente_qte", nullable = false)
    private Integer ligneVenteQte;

    @Column(name = "ligne_vente_total_ht")
    private Double ligneVenteTotalHT;

    @Column(name = "ligne_vente_total_ttc")
    private Double ligneVenteTotalTTC;

    @Column(name = "ligne_vente_prix_ht")
    private Double ligneVentePrixHT;

    @Column(name = "ligne_vente_prix_ttc")
    private Double ligneVentePrixTTC;

    @Column(name = "ligne_vente_designation")
    private String ligneVenteDesignation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ligneVentes")
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties("ligneVentes")
    private EnteteVente enteteVente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLigneVenteQte() {
        return ligneVenteQte;
    }

    public LigneVente ligneVenteQte(Integer ligneVenteQte) {
        this.ligneVenteQte = ligneVenteQte;
        return this;
    }

    public void setLigneVenteQte(Integer ligneVenteQte) {
        this.ligneVenteQte = ligneVenteQte;
    }

    public Double getLigneVenteTotalHT() {
        return ligneVenteTotalHT;
    }

    public LigneVente ligneVenteTotalHT(Double ligneVenteTotalHT) {
        this.ligneVenteTotalHT = ligneVenteTotalHT;
        return this;
    }

    public void setLigneVenteTotalHT(Double ligneVenteTotalHT) {
        this.ligneVenteTotalHT = ligneVenteTotalHT;
    }

    public Double getLigneVenteTotalTTC() {
        return ligneVenteTotalTTC;
    }

    public LigneVente ligneVenteTotalTTC(Double ligneVenteTotalTTC) {
        this.ligneVenteTotalTTC = ligneVenteTotalTTC;
        return this;
    }

    public void setLigneVenteTotalTTC(Double ligneVenteTotalTTC) {
        this.ligneVenteTotalTTC = ligneVenteTotalTTC;
    }

    public Double getLigneVentePrixHT() {
        return ligneVentePrixHT;
    }

    public LigneVente ligneVentePrixHT(Double ligneVentePrixHT) {
        this.ligneVentePrixHT = ligneVentePrixHT;
        return this;
    }

    public void setLigneVentePrixHT(Double ligneVentePrixHT) {
        this.ligneVentePrixHT = ligneVentePrixHT;
    }

    public Double getLigneVentePrixTTC() {
        return ligneVentePrixTTC;
    }

    public LigneVente ligneVentePrixTTC(Double ligneVentePrixTTC) {
        this.ligneVentePrixTTC = ligneVentePrixTTC;
        return this;
    }

    public void setLigneVentePrixTTC(Double ligneVentePrixTTC) {
        this.ligneVentePrixTTC = ligneVentePrixTTC;
    }

    public String getLigneVenteDesignation() {
        return ligneVenteDesignation;
    }

    public LigneVente ligneVenteDesignation(String ligneVenteDesignation) {
        this.ligneVenteDesignation = ligneVenteDesignation;
        return this;
    }

    public void setLigneVenteDesignation(String ligneVenteDesignation) {
        this.ligneVenteDesignation = ligneVenteDesignation;
    }

    public Produit getProduit() {
        return produit;
    }

    public LigneVente produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public EnteteVente getEnteteVente() {
        return enteteVente;
    }

    public LigneVente enteteVente(EnteteVente enteteVente) {
        this.enteteVente = enteteVente;
        return this;
    }

    public void setEnteteVente(EnteteVente enteteVente) {
        this.enteteVente = enteteVente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneVente)) {
            return false;
        }
        return id != null && id.equals(((LigneVente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LigneVente{" +
            "id=" + getId() +
            ", ligneVenteQte=" + getLigneVenteQte() +
            ", ligneVenteTotalHT=" + getLigneVenteTotalHT() +
            ", ligneVenteTotalTTC=" + getLigneVenteTotalTTC() +
            ", ligneVentePrixHT=" + getLigneVentePrixHT() +
            ", ligneVentePrixTTC=" + getLigneVentePrixTTC() +
            ", ligneVenteDesignation='" + getLigneVenteDesignation() + "'" +
            "}";
    }
}

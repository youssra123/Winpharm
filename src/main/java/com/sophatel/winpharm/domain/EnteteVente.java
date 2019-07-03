package com.sophatel.winpharm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A EnteteVente.
 */
@Entity
@Table(name = "entete_vente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnteteVente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "entete_vente_type", nullable = false)
    private String enteteVenteType;

    @Column(name = "entete_vente_total_ht")
    private Double enteteVenteTotalHT;

    @Column(name = "entete_vente_total_ttc")
    private Double enteteVenteTotalTTC;

    @Column(name = "entete_vente_date_creation")
    private ZonedDateTime enteteVenteDateCreation;

    @ManyToOne
    @JsonIgnoreProperties("enteteVentes")
    private Client client;

    @OneToMany(mappedBy = "enteteVente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LigneVente> ligneVentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnteteVenteType() {
        return enteteVenteType;
    }

    public EnteteVente enteteVenteType(String enteteVenteType) {
        this.enteteVenteType = enteteVenteType;
        return this;
    }

    public void setEnteteVenteType(String enteteVenteType) {
        this.enteteVenteType = enteteVenteType;
    }

    public Double getEnteteVenteTotalHT() {
        return enteteVenteTotalHT;
    }

    public EnteteVente enteteVenteTotalHT(Double enteteVenteTotalHT) {
        this.enteteVenteTotalHT = enteteVenteTotalHT;
        return this;
    }

    public void setEnteteVenteTotalHT(Double enteteVenteTotalHT) {
        this.enteteVenteTotalHT = enteteVenteTotalHT;
    }

    public Double getEnteteVenteTotalTTC() {
        return enteteVenteTotalTTC;
    }

    public EnteteVente enteteVenteTotalTTC(Double enteteVenteTotalTTC) {
        this.enteteVenteTotalTTC = enteteVenteTotalTTC;
        return this;
    }

    public void setEnteteVenteTotalTTC(Double enteteVenteTotalTTC) {
        this.enteteVenteTotalTTC = enteteVenteTotalTTC;
    }

    public ZonedDateTime getEnteteVenteDateCreation() {
        return enteteVenteDateCreation;
    }

    public EnteteVente enteteVenteDateCreation(ZonedDateTime enteteVenteDateCreation) {
        this.enteteVenteDateCreation = enteteVenteDateCreation;
        return this;
    }

    public void setEnteteVenteDateCreation(ZonedDateTime enteteVenteDateCreation) {
        this.enteteVenteDateCreation = enteteVenteDateCreation;
    }

    public Client getClient() {
        return client;
    }

    public EnteteVente client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<LigneVente> getLigneVentes() {
        return ligneVentes;
    }

    public EnteteVente ligneVentes(Set<LigneVente> ligneVentes) {
        this.ligneVentes = ligneVentes;
        return this;
    }

    public EnteteVente addLigneVente(LigneVente ligneVente) {
        this.ligneVentes.add(ligneVente);
        ligneVente.setEnteteVente(this);
        return this;
    }

    public EnteteVente removeLigneVente(LigneVente ligneVente) {
        this.ligneVentes.remove(ligneVente);
        ligneVente.setEnteteVente(null);
        return this;
    }

    public void setLigneVentes(Set<LigneVente> ligneVentes) {
        this.ligneVentes = ligneVentes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnteteVente)) {
            return false;
        }
        return id != null && id.equals(((EnteteVente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EnteteVente{" +
            "id=" + getId() +
            ", enteteVenteType='" + getEnteteVenteType() + "'" +
            ", enteteVenteTotalHT=" + getEnteteVenteTotalHT() +
            ", enteteVenteTotalTTC=" + getEnteteVenteTotalTTC() +
            ", enteteVenteDateCreation='" + getEnteteVenteDateCreation() + "'" +
            "}";
    }
}

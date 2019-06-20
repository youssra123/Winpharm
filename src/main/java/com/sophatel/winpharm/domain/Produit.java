package com.sophatel.winpharm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(name = "produit_libelle", length = 30, nullable = false)
    private String produitLibelle;

    @NotNull
    @Column(name = "produit_actif", nullable = false)
    private String produitActif;

    @Column(name = "produit_code_barre")
    private Integer produitCodeBarre;

    @Column(name = "produit_dosage")
    private Double produitDosage;

    @Column(name = "produit_uni_dosage")
    private String produitUniDosage;

    @Column(name = "produit_volume")
    private Double produitVolume;

    @Column(name = "produit_uni_volume")
    private String produitUniVolume;

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private Rayon produit_rayon;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produits")
    private Categorie produit_categorie;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produits")
    private FammilleTarifaire produit_fam_tar;

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private Laboratoire produit_laboratoire;

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private Grossiste produit_grossiste;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produits")
    private Forme proform;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduitLibelle() {
        return produitLibelle;
    }

    public Produit produitLibelle(String produitLibelle) {
        this.produitLibelle = produitLibelle;
        return this;
    }

    public void setProduitLibelle(String produitLibelle) {
        this.produitLibelle = produitLibelle;
    }

    public String getProduitActif() {
        return produitActif;
    }

    public Produit produitActif(String produitActif) {
        this.produitActif = produitActif;
        return this;
    }

    public void setProduitActif(String produitActif) {
        this.produitActif = produitActif;
    }

    public Integer getProduitCodeBarre() {
        return produitCodeBarre;
    }

    public Produit produitCodeBarre(Integer produitCodeBarre) {
        this.produitCodeBarre = produitCodeBarre;
        return this;
    }

    public void setProduitCodeBarre(Integer produitCodeBarre) {
        this.produitCodeBarre = produitCodeBarre;
    }

    public Double getProduitDosage() {
        return produitDosage;
    }

    public Produit produitDosage(Double produitDosage) {
        this.produitDosage = produitDosage;
        return this;
    }

    public void setProduitDosage(Double produitDosage) {
        this.produitDosage = produitDosage;
    }

    public String getProduitUniDosage() {
        return produitUniDosage;
    }

    public Produit produitUniDosage(String produitUniDosage) {
        this.produitUniDosage = produitUniDosage;
        return this;
    }

    public void setProduitUniDosage(String produitUniDosage) {
        this.produitUniDosage = produitUniDosage;
    }

    public Double getProduitVolume() {
        return produitVolume;
    }

    public Produit produitVolume(Double produitVolume) {
        this.produitVolume = produitVolume;
        return this;
    }

    public void setProduitVolume(Double produitVolume) {
        this.produitVolume = produitVolume;
    }

    public String getProduitUniVolume() {
        return produitUniVolume;
    }

    public Produit produitUniVolume(String produitUniVolume) {
        this.produitUniVolume = produitUniVolume;
        return this;
    }

    public void setProduitUniVolume(String produitUniVolume) {
        this.produitUniVolume = produitUniVolume;
    }

    public Rayon getProduit_rayon() {
        return produit_rayon;
    }

    public Produit produit_rayon(Rayon rayon) {
        this.produit_rayon = rayon;
        return this;
    }

    public void setProduit_rayon(Rayon rayon) {
        this.produit_rayon = rayon;
    }

    public Categorie getProduit_categorie() {
        return produit_categorie;
    }

    public Produit produit_categorie(Categorie categorie) {
        this.produit_categorie = categorie;
        return this;
    }

    public void setProduit_categorie(Categorie categorie) {
        this.produit_categorie = categorie;
    }

    public FammilleTarifaire getProduit_fam_tar() {
        return produit_fam_tar;
    }

    public Produit produit_fam_tar(FammilleTarifaire fammilleTarifaire) {
        this.produit_fam_tar = fammilleTarifaire;
        return this;
    }

    public void setProduit_fam_tar(FammilleTarifaire fammilleTarifaire) {
        this.produit_fam_tar = fammilleTarifaire;
    }

    public Laboratoire getProduit_laboratoire() {
        return produit_laboratoire;
    }

    public Produit produit_laboratoire(Laboratoire laboratoire) {
        this.produit_laboratoire = laboratoire;
        return this;
    }

    public void setProduit_laboratoire(Laboratoire laboratoire) {
        this.produit_laboratoire = laboratoire;
    }

    public Grossiste getProduit_grossiste() {
        return produit_grossiste;
    }

    public Produit produit_grossiste(Grossiste grossiste) {
        this.produit_grossiste = grossiste;
        return this;
    }

    public void setProduit_grossiste(Grossiste grossiste) {
        this.produit_grossiste = grossiste;
    }

    public Forme getProform() {
        return proform;
    }

    public Produit proform(Forme forme) {
        this.proform = forme;
        return this;
    }

    public void setProform(Forme forme) {
        this.proform = forme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", produitLibelle='" + getProduitLibelle() + "'" +
            ", produitActif='" + getProduitActif() + "'" +
            ", produitCodeBarre=" + getProduitCodeBarre() +
            ", produitDosage=" + getProduitDosage() +
            ", produitUniDosage='" + getProduitUniDosage() + "'" +
            ", produitVolume=" + getProduitVolume() +
            ", produitUniVolume='" + getProduitUniVolume() + "'" +
            "}";
    }
}

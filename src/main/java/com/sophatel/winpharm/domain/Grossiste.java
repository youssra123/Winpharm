package com.sophatel.winpharm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Grossiste.
 */
@Entity
@Table(name = "grossiste")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grossiste implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "grossiste_rais_soc", length = 20, nullable = false)
    private String grossisteRaisSoc;

    @NotNull
    @Size(min = 5, max = 40)
    @Column(name = "grossiste_adresse", length = 40, nullable = false)
    private String grossisteAdresse;

    @NotNull
    @Size(min = 10, max = 20)
    @Column(name = "grossiste_telephone", length = 20, nullable = false)
    private String grossisteTelephone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("grossistes")
    private Ville grossis_ville;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrossisteRaisSoc() {
        return grossisteRaisSoc;
    }

    public Grossiste grossisteRaisSoc(String grossisteRaisSoc) {
        this.grossisteRaisSoc = grossisteRaisSoc;
        return this;
    }

    public void setGrossisteRaisSoc(String grossisteRaisSoc) {
        this.grossisteRaisSoc = grossisteRaisSoc;
    }

    public String getGrossisteAdresse() {
        return grossisteAdresse;
    }

    public Grossiste grossisteAdresse(String grossisteAdresse) {
        this.grossisteAdresse = grossisteAdresse;
        return this;
    }

    public void setGrossisteAdresse(String grossisteAdresse) {
        this.grossisteAdresse = grossisteAdresse;
    }

    public String getGrossisteTelephone() {
        return grossisteTelephone;
    }

    public Grossiste grossisteTelephone(String grossisteTelephone) {
        this.grossisteTelephone = grossisteTelephone;
        return this;
    }

    public void setGrossisteTelephone(String grossisteTelephone) {
        this.grossisteTelephone = grossisteTelephone;
    }

    public Ville getGrossis_ville() {
        return grossis_ville;
    }

    public Grossiste grossis_ville(Ville ville) {
        this.grossis_ville = ville;
        return this;
    }

    public void setGrossis_ville(Ville ville) {
        this.grossis_ville = ville;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grossiste)) {
            return false;
        }
        return id != null && id.equals(((Grossiste) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grossiste{" +
            "id=" + getId() +
            ", grossisteRaisSoc='" + getGrossisteRaisSoc() + "'" +
            ", grossisteAdresse='" + getGrossisteAdresse() + "'" +
            ", grossisteTelephone='" + getGrossisteTelephone() + "'" +
            "}";
    }
}

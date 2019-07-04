package com.sophatel.winpharm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Laboratoire.
 */
@Entity
@Table(name = "laboratoire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Laboratoire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(name = "laboratoire_rais_soc", length = 30, nullable = false)
    private String laboratoireRaisSoc;

    @NotNull
    @Size(min = 5, max = 40)
    @Column(name = "laboratoire_adresse", length = 40, nullable = false)
    private String laboratoireAdresse;

    @NotNull
    @Size(min = 10, max = 20)
    @Column(name = "laboratoire_telephone", length = 20, nullable = false)
    private String laboratoireTelephone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaboratoireRaisSoc() {
        return laboratoireRaisSoc;
    }

    public Laboratoire laboratoireRaisSoc(String laboratoireRaisSoc) {
        this.laboratoireRaisSoc = laboratoireRaisSoc;
        return this;
    }

    public void setLaboratoireRaisSoc(String laboratoireRaisSoc) {
        this.laboratoireRaisSoc = laboratoireRaisSoc;
    }

    public String getLaboratoireAdresse() {
        return laboratoireAdresse;
    }

    public Laboratoire laboratoireAdresse(String laboratoireAdresse) {
        this.laboratoireAdresse = laboratoireAdresse;
        return this;
    }

    public void setLaboratoireAdresse(String laboratoireAdresse) {
        this.laboratoireAdresse = laboratoireAdresse;
    }

    public String getLaboratoireTelephone() {
        return laboratoireTelephone;
    }

    public Laboratoire laboratoireTelephone(String laboratoireTelephone) {
        this.laboratoireTelephone = laboratoireTelephone;
        return this;
    }

    public void setLaboratoireTelephone(String laboratoireTelephone) {
        this.laboratoireTelephone = laboratoireTelephone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Laboratoire)) {
            return false;
        }
        return id != null && id.equals(((Laboratoire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Laboratoire{" +
            "id=" + getId() +
            ", laboratoireRaisSoc='" + getLaboratoireRaisSoc() + "'" +
            ", laboratoireAdresse='" + getLaboratoireAdresse() + "'" +
            ", laboratoireTelephone='" + getLaboratoireTelephone() + "'" +
            "}";
    }
}

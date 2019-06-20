package com.sophatel.winpharm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Rayon.
 */
@Entity
@Table(name = "rayon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rayon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "rayon_libelle", nullable = false)
    private String rayonLibelle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRayonLibelle() {
        return rayonLibelle;
    }

    public Rayon rayonLibelle(String rayonLibelle) {
        this.rayonLibelle = rayonLibelle;
        return this;
    }

    public void setRayonLibelle(String rayonLibelle) {
        this.rayonLibelle = rayonLibelle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rayon)) {
            return false;
        }
        return id != null && id.equals(((Rayon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rayon{" +
            "id=" + getId() +
            ", rayonLibelle='" + getRayonLibelle() + "'" +
            "}";
    }
}

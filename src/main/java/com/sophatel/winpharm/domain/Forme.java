package com.sophatel.winpharm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Forme.
 */
@Entity
@Table(name = "forme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Forme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "forme_libelle", length = 20, nullable = false)
    private String formeLibelle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormeLibelle() {
        return formeLibelle;
    }

    public Forme formeLibelle(String formeLibelle) {
        this.formeLibelle = formeLibelle;
        return this;
    }

    public void setFormeLibelle(String formeLibelle) {
        this.formeLibelle = formeLibelle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Forme)) {
            return false;
        }
        return id != null && id.equals(((Forme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Forme{" +
            "id=" + getId() +
            ", formeLibelle='" + getFormeLibelle() + "'" +
            "}";
    }
}

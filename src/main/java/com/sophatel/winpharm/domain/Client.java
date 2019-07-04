package com.sophatel.winpharm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "client_nom", nullable = false)
    private String clientNom;

    @Column(name = "client_adresse")
    private String clientAdresse;

    @NotNull
    @Size(min = 10, max = 20)
    @Column(name = "client_telephone", length = 20, nullable = false)
    private String clientTelephone;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EnteteVente> enteteVentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientNom() {
        return clientNom;
    }

    public Client clientNom(String clientNom) {
        this.clientNom = clientNom;
        return this;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getClientAdresse() {
        return clientAdresse;
    }

    public Client clientAdresse(String clientAdresse) {
        this.clientAdresse = clientAdresse;
        return this;
    }

    public void setClientAdresse(String clientAdresse) {
        this.clientAdresse = clientAdresse;
    }

    public String getClientTelephone() {
        return clientTelephone;
    }

    public Client clientTelephone(String clientTelephone) {
        this.clientTelephone = clientTelephone;
        return this;
    }

    public void setClientTelephone(String clientTelephone) {
        this.clientTelephone = clientTelephone;
    }

    public Set<EnteteVente> getEnteteVentes() {
        return enteteVentes;
    }

    public Client enteteVentes(Set<EnteteVente> enteteVentes) {
        this.enteteVentes = enteteVentes;
        return this;
    }

    public Client addEnteteVente(EnteteVente enteteVente) {
        this.enteteVentes.add(enteteVente);
        enteteVente.setClient(this);
        return this;
    }

    public Client removeEnteteVente(EnteteVente enteteVente) {
        this.enteteVentes.remove(enteteVente);
        enteteVente.setClient(null);
        return this;
    }

    public void setEnteteVentes(Set<EnteteVente> enteteVentes) {
        this.enteteVentes = enteteVentes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientNom='" + getClientNom() + "'" +
            ", clientAdresse='" + getClientAdresse() + "'" +
            ", clientTelephone='" + getClientTelephone() + "'" +
            "}";
    }
}

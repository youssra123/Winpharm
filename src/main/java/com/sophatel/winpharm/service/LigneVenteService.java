package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.LigneVente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link LigneVente}.
 */
public interface LigneVenteService {

    /**
     * Save a ligneVente.
     *
     * @param ligneVente the entity to save.
     * @return the persisted entity.
     */
    LigneVente save(LigneVente ligneVente);

    /**
     * Get all the ligneVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LigneVente> findAll(Pageable pageable);


    /**
     * Get the "id" ligneVente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneVente> findOne(Long id);

    /**
     * Delete the "id" ligneVente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

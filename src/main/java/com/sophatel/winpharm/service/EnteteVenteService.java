package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.EnteteVente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EnteteVente}.
 */
public interface EnteteVenteService {

    /**
     * Save a enteteVente.
     *
     * @param enteteVente the entity to save.
     * @return the persisted entity.
     */
    EnteteVente save(EnteteVente enteteVente);

    /**
     * Get all the enteteVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnteteVente> findAll(Pageable pageable);


    /**
     * Get the "id" enteteVente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnteteVente> findOne(Long id);

    /**
     * Delete the "id" enteteVente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

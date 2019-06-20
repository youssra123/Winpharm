package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Laboratoire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Laboratoire}.
 */
public interface LaboratoireService {

    /**
     * Save a laboratoire.
     *
     * @param laboratoire the entity to save.
     * @return the persisted entity.
     */
    Laboratoire save(Laboratoire laboratoire);

    /**
     * Get all the laboratoires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Laboratoire> findAll(Pageable pageable);


    /**
     * Get the "id" laboratoire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Laboratoire> findOne(Long id);

    /**
     * Delete the "id" laboratoire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

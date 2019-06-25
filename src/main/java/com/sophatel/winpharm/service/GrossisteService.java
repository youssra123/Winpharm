package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Grossiste;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Grossiste}.
 */
public interface GrossisteService {

    /**
     * Save a grossiste.
     *
     * @param grossiste the entity to save.
     * @return the persisted entity.
     */
    Grossiste save(Grossiste grossiste);

    /**
     * Get all the grossistes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Grossiste> findAll(Pageable pageable);

    /**
     * Get all the grossistes by libelle.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Grossiste> findAllByDes(String str, Pageable pageable);

    /**
     * Get the "id" grossiste.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Grossiste> findOne(Long id);

    /**
     * Delete the "id" grossiste.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

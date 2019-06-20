package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Rayon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Rayon}.
 */
public interface RayonService {

    /**
     * Save a rayon.
     *
     * @param rayon the entity to save.
     * @return the persisted entity.
     */
    Rayon save(Rayon rayon);

    /**
     * Get all the rayons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Rayon> findAll(Pageable pageable);


    /**
     * Get the "id" rayon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rayon> findOne(Long id);

    /**
     * Delete the "id" rayon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

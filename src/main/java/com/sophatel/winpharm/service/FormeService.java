package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Forme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Forme}.
 */
public interface FormeService {

    /**
     * Save a forme.
     *
     * @param forme the entity to save.
     * @return the persisted entity.
     */
    Forme save(Forme forme);

    /**
     * Get all the formes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Forme> findAll(Pageable pageable);


    /**
     * Get the "id" forme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Forme> findOne(Long id);

    /**
     * Delete the "id" forme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

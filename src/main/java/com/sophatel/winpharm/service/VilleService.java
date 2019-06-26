package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Ville;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Ville}.
 */
public interface VilleService {

    /**
     * Save a ville.
     *
     * @param ville the entity to save.
     * @return the persisted entity.
     */
    Ville save(Ville ville);

    /**
     * Get all the villes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ville> findAll(Pageable pageable);

    /**
     * Get all the villes by libelle.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ville> findAllByDes(String str, Pageable pageable);

    /**
     * Get the "id" ville.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ville> findOne(Long id);

    /**
     * Delete the "id" ville.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

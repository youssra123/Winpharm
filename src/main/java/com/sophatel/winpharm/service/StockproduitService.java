package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Stockproduit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Stockproduit}.
 */
public interface StockproduitService {

    /**
     * Save a stockproduit.
     *
     * @param stockproduit the entity to save.
     * @return the persisted entity.
     */
    Stockproduit save(Stockproduit stockproduit);

    /**
     * Get all the stockproduits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Stockproduit> findAll(Pageable pageable);


    /**
     * Get the "id" stockproduit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Stockproduit> findOne(Long id);

    /**
     * Delete the "id" stockproduit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

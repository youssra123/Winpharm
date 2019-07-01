package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Stock}.
 */
public interface StockService {

    /**
     * Save a stock.
     *
     * @param stock the entity to save.
     * @return the persisted entity.
     */
    Stock save(Stock stock);

    /**
     * Get all the stocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Stock> findAll(Pageable pageable);
    
    /**
     * Get all the StockDTO where Produit is {@code null}.
     *
     * @return the list of entities.
     */
    List<Stock> findAllWhereProduitIsNull();

    /**
     * Get the "id" stock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Stock> findOne(Long id);
    
    /**
     * Get the "id" stock by produit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Stock> findOneByProduit(Long id);

    /**
     * Delete the "id" stock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

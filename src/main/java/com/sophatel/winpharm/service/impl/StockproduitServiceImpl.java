package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.StockproduitService;
import com.sophatel.winpharm.domain.Stockproduit;
import com.sophatel.winpharm.repository.StockproduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Stockproduit}.
 */
@Service
@Transactional
public class StockproduitServiceImpl implements StockproduitService {

    private final Logger log = LoggerFactory.getLogger(StockproduitServiceImpl.class);

    private final StockproduitRepository stockproduitRepository;

    public StockproduitServiceImpl(StockproduitRepository stockproduitRepository) {
        this.stockproduitRepository = stockproduitRepository;
    }

    /**
     * Save a stockproduit.
     *
     * @param stockproduit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Stockproduit save(Stockproduit stockproduit) {
        log.debug("Request to save Stockproduit : {}", stockproduit);
        return stockproduitRepository.save(stockproduit);
    }

    /**
     * Get all the stockproduits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Stockproduit> findAll(Pageable pageable) {
        log.debug("Request to get all Stockproduits");
        return stockproduitRepository.findAll(pageable);
    }


    /**
     * Get one stockproduit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Stockproduit> findOne(Long id) {
        log.debug("Request to get Stockproduit : {}", id);
        return stockproduitRepository.findById(id);
    }

    /**
     * Delete the stockproduit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stockproduit : {}", id);
        stockproduitRepository.deleteById(id);
    }
}

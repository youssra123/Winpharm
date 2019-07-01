package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.ProduitService;
import com.sophatel.winpharm.service.StockService;
import com.sophatel.winpharm.domain.Produit;
import com.sophatel.winpharm.domain.Stock;
import com.sophatel.winpharm.repository.ProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Produit}.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;
    private final StockService stockService;

    public ProduitServiceImpl(ProduitRepository produitRepository, StockService stockService) {
        this.produitRepository = produitRepository;
        this.stockService = stockService;
    }

    /**
     * Save a produit.
     *
     * @param produit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Produit save(Produit produit) {
        log.debug("Request to save Produit : {}", produit);
        Produit newProduit = produitRepository.save(produit);
        if (produit.getStock() != null)
            stockService.save(produit.getStock());
        return newProduit;
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produit> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable);
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produit> findAllByDes(String str, Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAllByDes("%"+str.toUpperCase()+"%", pageable);
    }


    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Produit> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.get().getStock() != null)
            return produit;
        Optional<Stock> stock = stockService.findOneByProduit(id);
        produit.get().setStock(stock.get());
        return produit;
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }
}

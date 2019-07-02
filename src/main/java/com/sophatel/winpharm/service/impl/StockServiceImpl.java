package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.StockService;
import com.sophatel.winpharm.domain.Stock;
import com.sophatel.winpharm.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Stock}.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Save a stock.
     *
     * @param stock the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Stock save(Stock stock) {
        log.debug("Request to save Stock : {}", stock);
        return stockRepository.save(stock);
    }

    /**
     * Get all the stocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Stock> findAll(Pageable pageable) {
        log.debug("Request to get all Stocks");
        return stockRepository.findAll(pageable);
    }

    /**
    *  Get all the stocks where Produit is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Stock> findAllWhereProduitIsNull() {
        log.debug("Request to get all stocks where Produit is null");
        return StreamSupport
            .stream(stockRepository.findAll().spliterator(), false)
            .filter(stock -> stock.getProduit() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one stock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Stock> findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findById(id);
    }

    /**
     * Get one stock by produit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Stock> findOneByProduit(Long id) {
        log.debug("Request to get Stock with produit : {}", id);
        return stockRepository.findOneByProduit(id);
    }

    /**
     * Delete the stock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.deleteById(id);
    }
}

package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.Stockproduit;
import com.sophatel.winpharm.service.StockproduitService;
import com.sophatel.winpharm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sophatel.winpharm.domain.Stockproduit}.
 */
@RestController
@RequestMapping("/api")
public class StockproduitResource {

    private final Logger log = LoggerFactory.getLogger(StockproduitResource.class);

    private static final String ENTITY_NAME = "stockproduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockproduitService stockproduitService;

    public StockproduitResource(StockproduitService stockproduitService) {
        this.stockproduitService = stockproduitService;
    }

    /**
     * {@code POST  /stockproduits} : Create a new stockproduit.
     *
     * @param stockproduit the stockproduit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockproduit, or with status {@code 400 (Bad Request)} if the stockproduit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stockproduits")
    public ResponseEntity<Stockproduit> createStockproduit(@Valid @RequestBody Stockproduit stockproduit) throws URISyntaxException {
        log.debug("REST request to save Stockproduit : {}", stockproduit);
        if (stockproduit.getId() != null) {
            throw new BadRequestAlertException("A new stockproduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stockproduit result = stockproduitService.save(stockproduit);
        return ResponseEntity.created(new URI("/api/stockproduits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stockproduits} : Updates an existing stockproduit.
     *
     * @param stockproduit the stockproduit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockproduit,
     * or with status {@code 400 (Bad Request)} if the stockproduit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockproduit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stockproduits")
    public ResponseEntity<Stockproduit> updateStockproduit(@Valid @RequestBody Stockproduit stockproduit) throws URISyntaxException {
        log.debug("REST request to update Stockproduit : {}", stockproduit);
        if (stockproduit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stockproduit result = stockproduitService.save(stockproduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockproduit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stockproduits} : get all the stockproduits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockproduits in body.
     */
    @GetMapping("/stockproduits")
    public ResponseEntity<List<Stockproduit>> getAllStockproduits(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Stockproduits");
        Page<Stockproduit> page = stockproduitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stockproduits/:id} : get the "id" stockproduit.
     *
     * @param id the id of the stockproduit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockproduit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stockproduits/{id}")
    public ResponseEntity<Stockproduit> getStockproduit(@PathVariable Long id) {
        log.debug("REST request to get Stockproduit : {}", id);
        Optional<Stockproduit> stockproduit = stockproduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockproduit);
    }

    /**
     * {@code DELETE  /stockproduits/:id} : delete the "id" stockproduit.
     *
     * @param id the id of the stockproduit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stockproduits/{id}")
    public ResponseEntity<Void> deleteStockproduit(@PathVariable Long id) {
        log.debug("REST request to delete Stockproduit : {}", id);
        stockproduitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

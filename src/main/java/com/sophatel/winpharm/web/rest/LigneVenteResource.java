package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.LigneVente;
import com.sophatel.winpharm.service.LigneVenteService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.LigneVente}.
 */
@RestController
@RequestMapping("/api")
public class LigneVenteResource {

    private final Logger log = LoggerFactory.getLogger(LigneVenteResource.class);

    private static final String ENTITY_NAME = "ligneVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneVenteService ligneVenteService;

    public LigneVenteResource(LigneVenteService ligneVenteService) {
        this.ligneVenteService = ligneVenteService;
    }

    /**
     * {@code POST  /ligne-ventes} : Create a new ligneVente.
     *
     * @param ligneVente the ligneVente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneVente, or with status {@code 400 (Bad Request)} if the ligneVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-ventes")
    public ResponseEntity<LigneVente> createLigneVente(@Valid @RequestBody LigneVente ligneVente) throws URISyntaxException {
        log.debug("REST request to save LigneVente : {}", ligneVente);
        if (ligneVente.getId() != null) {
            throw new BadRequestAlertException("A new ligneVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneVente result = ligneVenteService.save(ligneVente);
        return ResponseEntity.created(new URI("/api/ligne-ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-ventes} : Updates an existing ligneVente.
     *
     * @param ligneVente the ligneVente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneVente,
     * or with status {@code 400 (Bad Request)} if the ligneVente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneVente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-ventes")
    public ResponseEntity<LigneVente> updateLigneVente(@Valid @RequestBody LigneVente ligneVente) throws URISyntaxException {
        log.debug("REST request to update LigneVente : {}", ligneVente);
        if (ligneVente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LigneVente result = ligneVenteService.save(ligneVente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneVente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ligne-ventes} : get all the ligneVentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneVentes in body.
     */
    @GetMapping("/ligne-ventes")
    public ResponseEntity<List<LigneVente>> getAllLigneVentes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of LigneVentes");
        Page<LigneVente> page = ligneVenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ligne-ventes/:id} : get the "id" ligneVente.
     *
     * @param id the id of the ligneVente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneVente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-ventes/{id}")
    public ResponseEntity<LigneVente> getLigneVente(@PathVariable Long id) {
        log.debug("REST request to get LigneVente : {}", id);
        Optional<LigneVente> ligneVente = ligneVenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneVente);
    }

    /**
     * {@code DELETE  /ligne-ventes/:id} : delete the "id" ligneVente.
     *
     * @param id the id of the ligneVente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-ventes/{id}")
    public ResponseEntity<Void> deleteLigneVente(@PathVariable Long id) {
        log.debug("REST request to delete LigneVente : {}", id);
        ligneVenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

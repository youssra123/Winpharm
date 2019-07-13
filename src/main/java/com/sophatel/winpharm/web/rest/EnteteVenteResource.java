package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.EnteteVente;
import com.sophatel.winpharm.service.EnteteVenteService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.EnteteVente}.
 */
@RestController
@RequestMapping("/api")
public class EnteteVenteResource {

    private final Logger log = LoggerFactory.getLogger(EnteteVenteResource.class);

    private static final String ENTITY_NAME = "enteteVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnteteVenteService enteteVenteService;

    public EnteteVenteResource(EnteteVenteService enteteVenteService) {
        this.enteteVenteService = enteteVenteService;
    }

    /**
     * {@code POST  /entete-ventes} : Create a new enteteVente.
     *
     * @param enteteVente the enteteVente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enteteVente, or with status {@code 400 (Bad Request)} if the enteteVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entete-ventes")
    public ResponseEntity<EnteteVente> createEnteteVente(@Valid @RequestBody EnteteVente enteteVente) throws URISyntaxException {
        log.debug("REST request to save EnteteVente : {}", enteteVente);
        if (enteteVente.getId() != null) {
            throw new BadRequestAlertException("A new enteteVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnteteVente result = enteteVenteService.save(enteteVente);
        return ResponseEntity.created(new URI("/api/entete-ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entete-ventes} : Updates an existing enteteVente.
     *
     * @param enteteVente the enteteVente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enteteVente,
     * or with status {@code 400 (Bad Request)} if the enteteVente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enteteVente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entete-ventes")
    public ResponseEntity<EnteteVente> updateEnteteVente(@Valid @RequestBody EnteteVente enteteVente) throws URISyntaxException {
        log.debug("REST request to update EnteteVente : {}", enteteVente);
        if (enteteVente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnteteVente result = enteteVenteService.save(enteteVente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enteteVente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entete-ventes} : get all the enteteVentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enteteVentes in body.
     */
    @GetMapping("/entete-ventes")
    public ResponseEntity<List<EnteteVente>> getAllEnteteVentes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of EnteteVentes");
        String str = "";
        Page<EnteteVente> page;
        if (queryParams.get("q") != null)
            str = queryParams.get("q").get(0);
        if (str != "")
            page = enteteVenteService.findAllByDate(str, pageable);
        else
            page = enteteVenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entete-ventes/:id} : get the "id" enteteVente.
     *
     * @param id the id of the enteteVente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enteteVente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entete-ventes/{id}")
    public ResponseEntity<EnteteVente> getEnteteVente(@PathVariable Long id) {
        log.debug("REST request to get EnteteVente : {}", id);
        Optional<EnteteVente> enteteVente = enteteVenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enteteVente);
    }

    /**
     * {@code DELETE  /entete-ventes/:id} : delete the "id" enteteVente.
     *
     * @param id the id of the enteteVente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entete-ventes/{id}")
    public ResponseEntity<Void> deleteEnteteVente(@PathVariable Long id) {
        log.debug("REST request to delete EnteteVente : {}", id);
        enteteVenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

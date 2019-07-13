package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.Grossiste;
import com.sophatel.winpharm.service.GrossisteService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.Grossiste}.
 */
@RestController
@RequestMapping("/api")
public class GrossisteResource {

    private final Logger log = LoggerFactory.getLogger(GrossisteResource.class);

    private static final String ENTITY_NAME = "grossiste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrossisteService grossisteService;

    public GrossisteResource(GrossisteService grossisteService) {
        this.grossisteService = grossisteService;
    }

    /**
     * {@code POST  /grossistes} : Create a new grossiste.
     *
     * @param grossiste the grossiste to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grossiste, or with status {@code 400 (Bad Request)} if the grossiste has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grossistes")
    public ResponseEntity<Grossiste> createGrossiste(@Valid @RequestBody Grossiste grossiste) throws URISyntaxException {
        log.debug("REST request to save Grossiste : {}", grossiste);
        if (grossiste.getId() != null) {
            throw new BadRequestAlertException("A new grossiste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grossiste result = grossisteService.save(grossiste);
        return ResponseEntity.created(new URI("/api/grossistes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grossistes} : Updates an existing grossiste.
     *
     * @param grossiste the grossiste to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grossiste,
     * or with status {@code 400 (Bad Request)} if the grossiste is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grossiste couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grossistes")
    public ResponseEntity<Grossiste> updateGrossiste(@Valid @RequestBody Grossiste grossiste) throws URISyntaxException {
        log.debug("REST request to update Grossiste : {}", grossiste);
        if (grossiste.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Grossiste result = grossisteService.save(grossiste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grossiste.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grossistes} : get all the grossistes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grossistes in body.
     */
    @GetMapping("/grossistes")
    public ResponseEntity<List<Grossiste>> getAllGrossistes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Grossistes");
        String str = "";
        Page<Grossiste> page;
        if (queryParams.get("q") != null)
            str = queryParams.get("q").get(0);
        if (str != "")
            page = grossisteService.findAllByDes(str, pageable);
        else
            page = grossisteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grossistes/:id} : get the "id" grossiste.
     *
     * @param id the id of the grossiste to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grossiste, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grossistes/{id}")
    public ResponseEntity<Grossiste> getGrossiste(@PathVariable Long id) {
        log.debug("REST request to get Grossiste : {}", id);
        Optional<Grossiste> grossiste = grossisteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grossiste);
    }

    /**
     * {@code DELETE  /grossistes/:id} : delete the "id" grossiste.
     *
     * @param id the id of the grossiste to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grossistes/{id}")
    public ResponseEntity<Void> deleteGrossiste(@PathVariable Long id) {
        log.debug("REST request to delete Grossiste : {}", id);
        grossisteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

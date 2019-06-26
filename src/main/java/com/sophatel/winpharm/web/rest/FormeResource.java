package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.Forme;
import com.sophatel.winpharm.service.FormeService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.Forme}.
 */
@RestController
@RequestMapping("/api")
public class FormeResource {

    private final Logger log = LoggerFactory.getLogger(FormeResource.class);

    private static final String ENTITY_NAME = "forme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormeService formeService;

    public FormeResource(FormeService formeService) {
        this.formeService = formeService;
    }

    /**
     * {@code POST  /formes} : Create a new forme.
     *
     * @param forme the forme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forme, or with status {@code 400 (Bad Request)} if the forme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formes")
    public ResponseEntity<Forme> createForme(@Valid @RequestBody Forme forme) throws URISyntaxException {
        log.debug("REST request to save Forme : {}", forme);
        if (forme.getId() != null) {
            throw new BadRequestAlertException("A new forme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Forme result = formeService.save(forme);
        return ResponseEntity.created(new URI("/api/formes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formes} : Updates an existing forme.
     *
     * @param forme the forme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forme,
     * or with status {@code 400 (Bad Request)} if the forme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formes")
    public ResponseEntity<Forme> updateForme(@Valid @RequestBody Forme forme) throws URISyntaxException {
        log.debug("REST request to update Forme : {}", forme);
        if (forme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Forme result = formeService.save(forme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, forme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /formes} : get all the formes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formes in body.
     */
    @GetMapping("/formes")
    public ResponseEntity<List<Forme>> getAllFormes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Formes");
        String str = "";
        Page<Forme> page;
        if (queryParams.get("q") != null)
            str = queryParams.get("q").get(0);
        if (str != "")
            page = formeService.findAllByDes(str, pageable);
        else
            page = formeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formes/:id} : get the "id" forme.
     *
     * @param id the id of the forme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formes/{id}")
    public ResponseEntity<Forme> getForme(@PathVariable Long id) {
        log.debug("REST request to get Forme : {}", id);
        Optional<Forme> forme = formeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(forme);
    }

    /**
     * {@code DELETE  /formes/:id} : delete the "id" forme.
     *
     * @param id the id of the forme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formes/{id}")
    public ResponseEntity<Void> deleteForme(@PathVariable Long id) {
        log.debug("REST request to delete Forme : {}", id);
        formeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

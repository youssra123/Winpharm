package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.Rayon;
import com.sophatel.winpharm.service.RayonService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.Rayon}.
 */
@RestController
@RequestMapping("/api")
public class RayonResource {

    private final Logger log = LoggerFactory.getLogger(RayonResource.class);

    private static final String ENTITY_NAME = "rayon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RayonService rayonService;

    public RayonResource(RayonService rayonService) {
        this.rayonService = rayonService;
    }

    /**
     * {@code POST  /rayons} : Create a new rayon.
     *
     * @param rayon the rayon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rayon, or with status {@code 400 (Bad Request)} if the rayon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rayons")
    public ResponseEntity<Rayon> createRayon(@Valid @RequestBody Rayon rayon) throws URISyntaxException {
        log.debug("REST request to save Rayon : {}", rayon);
        if (rayon.getId() != null) {
            throw new BadRequestAlertException("A new rayon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rayon result = rayonService.save(rayon);
        return ResponseEntity.created(new URI("/api/rayons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rayons} : Updates an existing rayon.
     *
     * @param rayon the rayon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rayon,
     * or with status {@code 400 (Bad Request)} if the rayon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rayon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rayons")
    public ResponseEntity<Rayon> updateRayon(@Valid @RequestBody Rayon rayon) throws URISyntaxException {
        log.debug("REST request to update Rayon : {}", rayon);
        if (rayon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rayon result = rayonService.save(rayon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rayon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rayons} : get all the rayons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rayons in body.
     */
    @GetMapping("/rayons")
    public ResponseEntity<List<Rayon>> getAllRayons(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Rayons");
        String str = "";
        Page<Rayon> page;
        if (queryParams.get("q") != null)
            str = queryParams.get("q").get(0);
        if (str != "")
            page = rayonService.findAllByDes(str, pageable);
        else
            page = rayonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rayons/:id} : get the "id" rayon.
     *
     * @param id the id of the rayon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rayon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rayons/{id}")
    public ResponseEntity<Rayon> getRayon(@PathVariable Long id) {
        log.debug("REST request to get Rayon : {}", id);
        Optional<Rayon> rayon = rayonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rayon);
    }

    /**
     * {@code DELETE  /rayons/:id} : delete the "id" rayon.
     *
     * @param id the id of the rayon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rayons/{id}")
    public ResponseEntity<Void> deleteRayon(@PathVariable Long id) {
        log.debug("REST request to delete Rayon : {}", id);
        rayonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

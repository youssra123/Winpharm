package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.Laboratoire;
import com.sophatel.winpharm.service.LaboratoireService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.Laboratoire}.
 */
@RestController
@RequestMapping("/api")
public class LaboratoireResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoireResource.class);

    private static final String ENTITY_NAME = "laboratoire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoireService laboratoireService;

    public LaboratoireResource(LaboratoireService laboratoireService) {
        this.laboratoireService = laboratoireService;
    }

    /**
     * {@code POST  /laboratoires} : Create a new laboratoire.
     *
     * @param laboratoire the laboratoire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laboratoire, or with status {@code 400 (Bad Request)} if the laboratoire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laboratoires")
    public ResponseEntity<Laboratoire> createLaboratoire(@Valid @RequestBody Laboratoire laboratoire) throws URISyntaxException {
        log.debug("REST request to save Laboratoire : {}", laboratoire);
        if (laboratoire.getId() != null) {
            throw new BadRequestAlertException("A new laboratoire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Laboratoire result = laboratoireService.save(laboratoire);
        return ResponseEntity.created(new URI("/api/laboratoires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /laboratoires} : Updates an existing laboratoire.
     *
     * @param laboratoire the laboratoire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoire,
     * or with status {@code 400 (Bad Request)} if the laboratoire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the laboratoire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/laboratoires")
    public ResponseEntity<Laboratoire> updateLaboratoire(@Valid @RequestBody Laboratoire laboratoire) throws URISyntaxException {
        log.debug("REST request to update Laboratoire : {}", laboratoire);
        if (laboratoire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Laboratoire result = laboratoireService.save(laboratoire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /laboratoires} : get all the laboratoires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of laboratoires in body.
     */
    @GetMapping("/laboratoires")
    public ResponseEntity<List<Laboratoire>> getAllLaboratoires(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Laboratoires");
        String str = "";
        Page<Laboratoire> page;
        if (queryParams.get("q") != null)
            str = queryParams.get("q").get(0);
        if (str != "")
            page = laboratoireService.findAllByDes(str, pageable);
        else
            page = laboratoireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /laboratoires/:id} : get the "id" laboratoire.
     *
     * @param id the id of the laboratoire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the laboratoire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratoires/{id}")
    public ResponseEntity<Laboratoire> getLaboratoire(@PathVariable Long id) {
        log.debug("REST request to get Laboratoire : {}", id);
        Optional<Laboratoire> laboratoire = laboratoireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(laboratoire);
    }

    /**
     * {@code DELETE  /laboratoires/:id} : delete the "id" laboratoire.
     *
     * @param id the id of the laboratoire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/laboratoires/{id}")
    public ResponseEntity<Void> deleteLaboratoire(@PathVariable Long id) {
        log.debug("REST request to delete Laboratoire : {}", id);
        laboratoireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

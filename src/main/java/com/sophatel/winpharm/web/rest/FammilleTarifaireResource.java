package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.domain.FammilleTarifaire;
import com.sophatel.winpharm.service.FammilleTarifaireService;
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
 * REST controller for managing {@link com.sophatel.winpharm.domain.FammilleTarifaire}.
 */
@RestController
@RequestMapping("/api")
public class FammilleTarifaireResource {

    private final Logger log = LoggerFactory.getLogger(FammilleTarifaireResource.class);

    private static final String ENTITY_NAME = "fammilleTarifaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FammilleTarifaireService fammilleTarifaireService;

    public FammilleTarifaireResource(FammilleTarifaireService fammilleTarifaireService) {
        this.fammilleTarifaireService = fammilleTarifaireService;
    }

    /**
     * {@code POST  /fammille-tarifaires} : Create a new fammilleTarifaire.
     *
     * @param fammilleTarifaire the fammilleTarifaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fammilleTarifaire, or with status {@code 400 (Bad Request)} if the fammilleTarifaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fammille-tarifaires")
    public ResponseEntity<FammilleTarifaire> createFammilleTarifaire(@Valid @RequestBody FammilleTarifaire fammilleTarifaire) throws URISyntaxException {
        log.debug("REST request to save FammilleTarifaire : {}", fammilleTarifaire);
        if (fammilleTarifaire.getId() != null) {
            throw new BadRequestAlertException("A new fammilleTarifaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FammilleTarifaire result = fammilleTarifaireService.save(fammilleTarifaire);
        return ResponseEntity.created(new URI("/api/fammille-tarifaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fammille-tarifaires} : Updates an existing fammilleTarifaire.
     *
     * @param fammilleTarifaire the fammilleTarifaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fammilleTarifaire,
     * or with status {@code 400 (Bad Request)} if the fammilleTarifaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fammilleTarifaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fammille-tarifaires")
    public ResponseEntity<FammilleTarifaire> updateFammilleTarifaire(@Valid @RequestBody FammilleTarifaire fammilleTarifaire) throws URISyntaxException {
        log.debug("REST request to update FammilleTarifaire : {}", fammilleTarifaire);
        if (fammilleTarifaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FammilleTarifaire result = fammilleTarifaireService.save(fammilleTarifaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fammilleTarifaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fammille-tarifaires} : get all the fammilleTarifaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fammilleTarifaires in body.
     */
    @GetMapping("/fammille-tarifaires")
    public ResponseEntity<List<FammilleTarifaire>> getAllFammilleTarifaires(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of FammilleTarifaires");
        String str = "";
        Page<FammilleTarifaire> page;
        if (queryParams.get("q") != null)
            str = queryParams.get("q").get(0);
        if (str != "")
            page = fammilleTarifaireService.findAllByDes(str, pageable);
        else
            page = fammilleTarifaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fammille-tarifaires/:id} : get the "id" fammilleTarifaire.
     *
     * @param id the id of the fammilleTarifaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fammilleTarifaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fammille-tarifaires/{id}")
    public ResponseEntity<FammilleTarifaire> getFammilleTarifaire(@PathVariable Long id) {
        log.debug("REST request to get FammilleTarifaire : {}", id);
        Optional<FammilleTarifaire> fammilleTarifaire = fammilleTarifaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fammilleTarifaire);
    }

    /**
     * {@code DELETE  /fammille-tarifaires/:id} : delete the "id" fammilleTarifaire.
     *
     * @param id the id of the fammilleTarifaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fammille-tarifaires/{id}")
    public ResponseEntity<Void> deleteFammilleTarifaire(@PathVariable Long id) {
        log.debug("REST request to delete FammilleTarifaire : {}", id);
        fammilleTarifaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

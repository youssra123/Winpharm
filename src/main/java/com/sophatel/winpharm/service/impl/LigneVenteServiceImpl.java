package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.LigneVenteService;
import com.sophatel.winpharm.domain.LigneVente;
import com.sophatel.winpharm.repository.LigneVenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LigneVente}.
 */
@Service
@Transactional
public class LigneVenteServiceImpl implements LigneVenteService {

    private final Logger log = LoggerFactory.getLogger(LigneVenteServiceImpl.class);

    private final LigneVenteRepository ligneVenteRepository;

    public LigneVenteServiceImpl(LigneVenteRepository ligneVenteRepository) {
        this.ligneVenteRepository = ligneVenteRepository;
    }

    /**
     * Save a ligneVente.
     *
     * @param ligneVente the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LigneVente save(LigneVente ligneVente) {
        log.debug("Request to save LigneVente : {}", ligneVente);
        return ligneVenteRepository.save(ligneVente);
    }

    /**
     * Get all the ligneVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LigneVente> findAll(Pageable pageable) {
        log.debug("Request to get all LigneVentes");
        return ligneVenteRepository.findAll(pageable);
    }


    /**
     * Get one ligneVente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LigneVente> findOne(Long id) {
        log.debug("Request to get LigneVente : {}", id);
        return ligneVenteRepository.findById(id);
    }

    /**
     * Delete the ligneVente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneVente : {}", id);
        ligneVenteRepository.deleteById(id);
    }
}

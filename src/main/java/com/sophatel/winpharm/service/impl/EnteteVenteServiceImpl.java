package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.EnteteVenteService;
import com.sophatel.winpharm.domain.EnteteVente;
import com.sophatel.winpharm.repository.EnteteVenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EnteteVente}.
 */
@Service
@Transactional
public class EnteteVenteServiceImpl implements EnteteVenteService {

    private final Logger log = LoggerFactory.getLogger(EnteteVenteServiceImpl.class);

    private final EnteteVenteRepository enteteVenteRepository;

    public EnteteVenteServiceImpl(EnteteVenteRepository enteteVenteRepository) {
        this.enteteVenteRepository = enteteVenteRepository;
    }

    /**
     * Save a enteteVente.
     *
     * @param enteteVente the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EnteteVente save(EnteteVente enteteVente) {
        log.debug("Request to save EnteteVente : {}", enteteVente);
        return enteteVenteRepository.save(enteteVente);
    }

    /**
     * Get all the enteteVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnteteVente> findAll(Pageable pageable) {
        log.debug("Request to get all EnteteVentes");
        return enteteVenteRepository.findAll(pageable);
    }

    /**
     * Get all the enteteVentes by date.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnteteVente> findAllByDate(String str, Pageable pageable) {
        log.debug("Request to get all EnteteVentes by date");
        return enteteVenteRepository.findAllByDate(str, pageable);
    }

    /**
     * Get one enteteVente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnteteVente> findOne(Long id) {
        log.debug("Request to get EnteteVente : {}", id);
        return enteteVenteRepository.findById(id);
    }

    /**
     * Delete the enteteVente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EnteteVente : {}", id);
        enteteVenteRepository.deleteById(id);
    }
}

package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.GrossisteService;
import com.sophatel.winpharm.domain.Grossiste;
import com.sophatel.winpharm.repository.GrossisteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Grossiste}.
 */
@Service
@Transactional
public class GrossisteServiceImpl implements GrossisteService {

    private final Logger log = LoggerFactory.getLogger(GrossisteServiceImpl.class);

    private final GrossisteRepository grossisteRepository;

    public GrossisteServiceImpl(GrossisteRepository grossisteRepository) {
        this.grossisteRepository = grossisteRepository;
    }

    /**
     * Save a grossiste.
     *
     * @param grossiste the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Grossiste save(Grossiste grossiste) {
        log.debug("Request to save Grossiste : {}", grossiste);
        return grossisteRepository.save(grossiste);
    }

    /**
     * Get all the grossistes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Grossiste> findAll(Pageable pageable) {
        log.debug("Request to get all Grossistes");
        return grossisteRepository.findAll(pageable);
    }


    /**
     * Get one grossiste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Grossiste> findOne(Long id) {
        log.debug("Request to get Grossiste : {}", id);
        return grossisteRepository.findById(id);
    }

    /**
     * Delete the grossiste by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grossiste : {}", id);
        grossisteRepository.deleteById(id);
    }
}

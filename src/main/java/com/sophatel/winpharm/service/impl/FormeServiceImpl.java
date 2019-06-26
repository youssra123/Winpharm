package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.FormeService;
import com.sophatel.winpharm.domain.Forme;
import com.sophatel.winpharm.repository.FormeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Forme}.
 */
@Service
@Transactional
public class FormeServiceImpl implements FormeService {

    private final Logger log = LoggerFactory.getLogger(FormeServiceImpl.class);

    private final FormeRepository formeRepository;

    public FormeServiceImpl(FormeRepository formeRepository) {
        this.formeRepository = formeRepository;
    }

    /**
     * Save a forme.
     *
     * @param forme the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Forme save(Forme forme) {
        log.debug("Request to save Forme : {}", forme);
        return formeRepository.save(forme);
    }

    /**
     * Get all the formes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Forme> findAll(Pageable pageable) {
        log.debug("Request to get all Formes");
        return formeRepository.findAll(pageable);
    }
    
    /**
     * Get all the formes by libelle.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Forme> findAllByDes(String str, Pageable pageable) {
        log.debug("Request to get all Formes by libelle");
        return formeRepository.findAllByDes("%"+str.toUpperCase()+"%", pageable);
    }

    /**
     * Get one forme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Forme> findOne(Long id) {
        log.debug("Request to get Forme : {}", id);
        return formeRepository.findById(id);
    }

    /**
     * Delete the forme by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Forme : {}", id);
        formeRepository.deleteById(id);
    }
}

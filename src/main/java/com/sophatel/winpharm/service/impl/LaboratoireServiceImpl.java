package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.LaboratoireService;
import com.sophatel.winpharm.domain.Laboratoire;
import com.sophatel.winpharm.repository.LaboratoireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Laboratoire}.
 */
@Service
@Transactional
public class LaboratoireServiceImpl implements LaboratoireService {

    private final Logger log = LoggerFactory.getLogger(LaboratoireServiceImpl.class);

    private final LaboratoireRepository laboratoireRepository;

    public LaboratoireServiceImpl(LaboratoireRepository laboratoireRepository) {
        this.laboratoireRepository = laboratoireRepository;
    }

    /**
     * Save a laboratoire.
     *
     * @param laboratoire the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Laboratoire save(Laboratoire laboratoire) {
        log.debug("Request to save Laboratoire : {}", laboratoire);
        return laboratoireRepository.save(laboratoire);
    }

    /**
     * Get all the laboratoires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Laboratoire> findAll(Pageable pageable) {
        log.debug("Request to get all Laboratoires");
        return laboratoireRepository.findAll(pageable);
    }

    /**
     * Get all the laboratoires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Laboratoire> findAllByDes(String str, Pageable pageable) {
        log.debug("Request to get all Laboratoires by libelle");
        return laboratoireRepository.findAllByDes("%"+str.toUpperCase()+"%", pageable);
    }

    /**
     * Get one laboratoire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratoire> findOne(Long id) {
        log.debug("Request to get Laboratoire : {}", id);
        return laboratoireRepository.findById(id);
    }

    /**
     * Delete the laboratoire by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Laboratoire : {}", id);
        laboratoireRepository.deleteById(id);
    }
}

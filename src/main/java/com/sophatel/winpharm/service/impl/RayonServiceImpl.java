package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.RayonService;
import com.sophatel.winpharm.domain.Rayon;
import com.sophatel.winpharm.repository.RayonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rayon}.
 */
@Service
@Transactional
public class RayonServiceImpl implements RayonService {

    private final Logger log = LoggerFactory.getLogger(RayonServiceImpl.class);

    private final RayonRepository rayonRepository;

    public RayonServiceImpl(RayonRepository rayonRepository) {
        this.rayonRepository = rayonRepository;
    }

    /**
     * Save a rayon.
     *
     * @param rayon the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Rayon save(Rayon rayon) {
        log.debug("Request to save Rayon : {}", rayon);
        return rayonRepository.save(rayon);
    }

    /**
     * Get all the rayons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Rayon> findAll(Pageable pageable) {
        log.debug("Request to get all Rayons");
        return rayonRepository.findAll(pageable);
    }

    /**
     * Get all the rayons by desc.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Rayon> findAllByDes(String str, Pageable pageable) {
        log.debug("Request to get all Rayons By libelle");
        return rayonRepository.findAllByDes("%"+str+"%", pageable);
    }


    /**
     * Get one rayon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rayon> findOne(Long id) {
        log.debug("Request to get Rayon : {}", id);
        return rayonRepository.findById(id);
    }

    /**
     * Delete the rayon by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rayon : {}", id);
        rayonRepository.deleteById(id);
    }
}

package com.sophatel.winpharm.service.impl;

import com.sophatel.winpharm.service.FammilleTarifaireService;
import com.sophatel.winpharm.domain.FammilleTarifaire;
import com.sophatel.winpharm.repository.FammilleTarifaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FammilleTarifaire}.
 */
@Service
@Transactional
public class FammilleTarifaireServiceImpl implements FammilleTarifaireService {

    private final Logger log = LoggerFactory.getLogger(FammilleTarifaireServiceImpl.class);

    private final FammilleTarifaireRepository fammilleTarifaireRepository;

    public FammilleTarifaireServiceImpl(FammilleTarifaireRepository fammilleTarifaireRepository) {
        this.fammilleTarifaireRepository = fammilleTarifaireRepository;
    }

    /**
     * Save a fammilleTarifaire.
     *
     * @param fammilleTarifaire the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FammilleTarifaire save(FammilleTarifaire fammilleTarifaire) {
        log.debug("Request to save FammilleTarifaire : {}", fammilleTarifaire);
        return fammilleTarifaireRepository.save(fammilleTarifaire);
    }

    /**
     * Get all the fammilleTarifaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FammilleTarifaire> findAll(Pageable pageable) {
        log.debug("Request to get all FammilleTarifaires");
        return fammilleTarifaireRepository.findAll(pageable);
    }


    /**
     * Get one fammilleTarifaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FammilleTarifaire> findOne(Long id) {
        log.debug("Request to get FammilleTarifaire : {}", id);
        return fammilleTarifaireRepository.findById(id);
    }

    /**
     * Delete the fammilleTarifaire by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FammilleTarifaire : {}", id);
        fammilleTarifaireRepository.deleteById(id);
    }
}

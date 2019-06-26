package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.FammilleTarifaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FammilleTarifaire}.
 */
public interface FammilleTarifaireService {

    /**
     * Save a fammilleTarifaire.
     *
     * @param fammilleTarifaire the entity to save.
     * @return the persisted entity.
     */
    FammilleTarifaire save(FammilleTarifaire fammilleTarifaire);

    /**
     * Get all the fammilleTarifaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FammilleTarifaire> findAll(Pageable pageable);

    /**
     * Get all the fammilleTarifaires by libelle.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FammilleTarifaire> findAllByDes(String str, Pageable pageable);


    /**
     * Get the "id" fammilleTarifaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FammilleTarifaire> findOne(Long id);

    /**
     * Delete the "id" fammilleTarifaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

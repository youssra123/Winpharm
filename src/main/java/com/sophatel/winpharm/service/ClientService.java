package com.sophatel.winpharm.service;

import com.sophatel.winpharm.domain.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Client}.
 */
public interface ClientService {

    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    Client save(Client client);

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Client> findAll(Pageable pageable);

    /**
     * Get all the clients by name.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Client> findAllByName(String str, Pageable pageable);

    /**
     * Get the "id" client.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Client> findOne(Long id);

    /**
     * Delete the "id" client.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

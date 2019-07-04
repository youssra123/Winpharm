package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c where c.clientNom like :x")
    public Page<Client> findAllByName(@Param("x") String str, Pageable pageable);
}

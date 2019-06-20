package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Grossiste;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Grossiste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrossisteRepository extends JpaRepository<Grossiste, Long> {

}

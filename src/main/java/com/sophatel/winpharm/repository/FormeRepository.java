package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Forme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Forme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormeRepository extends JpaRepository<Forme, Long> {

}

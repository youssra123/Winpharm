package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.EnteteVente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EnteteVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnteteVenteRepository extends JpaRepository<EnteteVente, Long> {

}

package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.LigneVente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LigneVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

}

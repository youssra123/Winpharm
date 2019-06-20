package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.FammilleTarifaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FammilleTarifaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FammilleTarifaireRepository extends JpaRepository<FammilleTarifaire, Long> {

}

package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.FammilleTarifaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FammilleTarifaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FammilleTarifaireRepository extends JpaRepository<FammilleTarifaire, Long> {
    @Query("select f from FammilleTarifaire f where f.famiTarifLibelle like :x")
    public Page<FammilleTarifaire> findAllByDes(@Param("x") String str, Pageable pageable);
}

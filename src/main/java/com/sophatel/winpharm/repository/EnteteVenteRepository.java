package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.EnteteVente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EnteteVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnteteVenteRepository extends JpaRepository<EnteteVente, Long> {
    @Query("select v from EnteteVente v where TO_CHAR(v.enteteVenteDateCreation,'YYYY-MM-DD') like :x")
    public Page<EnteteVente> findAllByDate(@Param("x") String str, Pageable pageable);
}

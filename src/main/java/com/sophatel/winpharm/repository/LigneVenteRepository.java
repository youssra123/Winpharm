package com.sophatel.winpharm.repository;

import java.util.Set;

import com.sophatel.winpharm.domain.LigneVente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LigneVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {
    @Query("select lv from LigneVente lv where lv.enteteVente.id = :x")
    public Page<LigneVente> findAllByVente(@Param("x") Long vente, Pageable pageable);

    @Query("select lv from LigneVente lv where lv.enteteVente.id = :x")
    public Set<LigneVente> findAllByVente(@Param("x") Long vente);
}

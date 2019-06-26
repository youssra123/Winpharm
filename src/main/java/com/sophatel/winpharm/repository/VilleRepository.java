package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Ville;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ville entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {
    @Query("select v from Ville v where upper(v.villeLibelle) like :x")
    public Page<Ville> findAllByDes(@Param("x") String str, Pageable pageable);
}

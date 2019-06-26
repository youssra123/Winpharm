package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Forme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Forme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormeRepository extends JpaRepository<Forme, Long> {
    @Query("select f from Forme f where upper(f.formeLibelle) like :x")
    public Page<Forme> findAllByDes(@Param("x") String str, Pageable pageable);
}

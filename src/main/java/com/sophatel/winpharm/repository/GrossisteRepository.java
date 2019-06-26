package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Grossiste;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Grossiste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrossisteRepository extends JpaRepository<Grossiste, Long> {
    @Query("select g from Grossiste g where g.grossisteRaisSoc like :x")
    public Page<Grossiste> findAllByDes(@Param("x") String str, Pageable pageable);
}

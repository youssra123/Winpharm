package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Laboratoire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Laboratoire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratoireRepository extends JpaRepository<Laboratoire, Long> {
    @Query("select l from Laboratoire l where upper(l.laboratoireRaisSoc) like :x")
    public Page<Laboratoire> findAllByDes(@Param("x") String str, Pageable pageable);
}

package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Rayon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rayon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RayonRepository extends JpaRepository<Rayon, Long> {
    @Query("select r from Rayon r where r.rayonLibelle like :x")
    public Page<Rayon> findAllByDes(@Param("x") String str, Pageable pageable);
}

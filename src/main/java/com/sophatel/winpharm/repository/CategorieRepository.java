package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Categorie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Categorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    @Query("select c from Categorie c where upper(c.categorieLibelle) like :x")
    public Page<Categorie> findAllByDes(@Param("x") String str, Pageable pageable);
}

package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Produit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("select p from Produit p where p.produitLibelle like :x")
    public Page<Produit> findAllByDes(@Param("x") String str, Pageable pageable);


    @Query("SELECT sum(sp.stock_produit_quantite)"+
    "from produit p,stockproduit sp"+
    "where p.id=sp.stock_produit_produit_id"+
    "group by p.id having p.id = :x")
    public  Integer calculQteStock(@Param("x") Long id);



}

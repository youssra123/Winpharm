package com.sophatel.winpharm.repository;

import java.util.Optional;

import com.sophatel.winpharm.domain.Stock;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("select s from Stock s where s.produit.id = :x")
    public Optional<Stock> findOneByProduit(@Param("x") Long id);
}

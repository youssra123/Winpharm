package com.sophatel.winpharm.repository;

import com.sophatel.winpharm.domain.Stockproduit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Stockproduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockproduitRepository extends JpaRepository<Stockproduit, Long> {

}

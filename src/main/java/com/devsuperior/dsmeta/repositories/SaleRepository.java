package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // SQL Nativo
    @Query(nativeQuery = true,
            value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS salesAmount "
                    + "FROM tb_sales "
                    + "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id "
                    + "WHERE tb_sales.date BETWEEN :minDate AND :maxDate "
                    + "GROUP BY tb_seller.name")
    List<SummaryProjection> findSummaryNativeSQL(LocalDate minDate, LocalDate maxDate);



    // JPQL
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
            + "FROM Sale obj "
            + "WHERE obj.date >= :minDate "
            + "AND obj.date <= :maxDate "
            + "GROUP BY obj.seller.name")
    List<SaleSummaryDTO> findSummaryJPQL(LocalDate minDate, LocalDate maxDate);

}

package com.example.alura.challenge.edition.n2.domain.repository;

import com.example.alura.challenge.edition.n2.domain.dto.summary.SummaryDTO;
import com.example.alura.challenge.edition.n2.domain.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SummaryDTO> getFinancialSummary(int year, int month) {
        String sql = "WITH " +
                "sum_receipts AS (SELECT SUM(r.value) AS Sum_Receipt FROM receipts r " +
                "WHERE YEAR(r.date) = :year AND MONTH(r.date) = :month), " +
                "sum_expenses AS (SELECT SUM(e.value) AS Sum_Expenses FROM expenses e " +
                "WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month), " +
                "sum_expenses_category AS (SELECT e.category AS Category, SUM(e.value) AS Sum_Expenses_Grouped " +
                "FROM expenses e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month GROUP BY e.category) " +
                "SELECT sp.Sum_Receipt AS All_Receipts, se.Sum_Expenses AS All_Expenses, " +
                "sp.Sum_Receipt - se.Sum_Expenses AS Final_Balance, " +
                "smc.Sum_Expenses_Grouped AS Expenses_By_Category, smc.Category " +
                "FROM sum_receipts sp, sum_expenses se, sum_expenses_category smc";


        Query query = entityManager.createNativeQuery(sql, "FinancialSummaryMapping");
        query.setParameter("year", year);
        query.setParameter("month", month);

        List<Object[]> results = query.getResultList();
        return results.stream().map(result -> new SummaryDTO(
                result[0] != null ? ((Number) result[0]).doubleValue() : null,
                result[1] != null ? ((Number) result[1]).doubleValue() : null,
                result[2] != null ? ((Number) result[2]).doubleValue() : null,
                result[3] != null ? ((Number) result[3]).doubleValue() : null,
                result[4] != null ? Category.valueOf((String) result[4]) : null
        )).collect(Collectors.toList());    }
}

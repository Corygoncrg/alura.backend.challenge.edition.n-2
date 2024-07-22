package com.example.alura.challenge.edition.n2.domain.repository;

import com.example.alura.challenge.edition.n2.domain.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, CustomRepository {
    Page<Expense> findAllByActiveTrue(Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE e.description = :description AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    Optional<Expense> findByDescriptionAndYearAndMonth(@Param("description") String description, @Param("year") int year, @Param("month") int month);

    Page<Expense> findAllByDescriptionContaining(String description, Pageable pageable);
    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")

    Page<Expense> findAllByYearAndMonth(Pageable pageable, int year, int month);
}

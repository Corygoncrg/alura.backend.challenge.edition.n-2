package com.example.alura.challenge.edition.n2.domain.repository;

import com.example.alura.challenge.edition.n2.domain.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>, CustomRepository {
    Page<Receipt> findAllByActiveTrue(Pageable pageable);

    @Query("SELECT r FROM Receipt r WHERE r.description = :description AND YEAR(r.date) = :year AND MONTH(r.date) = :month")
    Optional<Receipt> findByDescriptionAndYearAndMonth(@Param("description") String description, @Param("year") int year, @Param("month") int month);

    Page<Receipt> findAllByDescriptionContaining(String description, Pageable pageable);

    @Query("SELECT r FROM Receipt r WHERE YEAR(r.date) = :year AND MONTH(r.date) = :month")
    Page<Receipt> findAllByYearAndMonth(Pageable pageable, int year, int month);
}

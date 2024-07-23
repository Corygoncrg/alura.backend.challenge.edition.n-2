package com.example.alura.challenge.edition.n2.domain.dto.expense;

import com.example.alura.challenge.edition.n2.domain.model.Category;

import java.time.LocalDate;

public record ExpenseUpdateDTO(Long id, String description, Double value, LocalDate date, Category category) {
}

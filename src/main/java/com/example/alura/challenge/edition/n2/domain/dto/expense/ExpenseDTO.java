package com.example.alura.challenge.edition.n2.domain.dto.expense;

import com.example.alura.challenge.edition.n2.domain.model.Expense;

import java.time.LocalDate;

public record ExpenseDTO(Long id, String description, Double value, LocalDate date) {

    public ExpenseDTO(Expense expense) {
        this(expense.getId(), expense.getDescription(), expense.getValue(), expense.getDate());
    }
}

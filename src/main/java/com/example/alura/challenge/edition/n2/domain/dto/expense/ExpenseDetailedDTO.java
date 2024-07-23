package com.example.alura.challenge.edition.n2.domain.dto.expense;

import com.example.alura.challenge.edition.n2.domain.model.Category;
import com.example.alura.challenge.edition.n2.domain.model.Expense;

import java.time.LocalDate;

public record ExpenseDetailedDTO (Long id, String description, Double value, LocalDate date, Category category) {

    public ExpenseDetailedDTO(Expense expense) {
        this(expense.getId(), expense.getDescription(), expense.getValue(), expense.getDate(), Category.fromString(expense.getCategory().toString()));
    }
}

package com.example.alura.challenge.edition.n2.domain.dto.expense;

import com.example.alura.challenge.edition.n2.domain.model.Category;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExpenseRegisterDTO(
        @NotNull String description,
        @NotNull Double value,
        @NotNull LocalDate date,
        Category category
        ) {
}

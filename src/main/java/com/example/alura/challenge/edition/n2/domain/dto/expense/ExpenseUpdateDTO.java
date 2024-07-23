package com.example.alura.challenge.edition.n2.domain.dto.expense;

import java.time.LocalDate;

public record ExpenseUpdateDTO(Long id, String description, Double value, LocalDate date) {
}

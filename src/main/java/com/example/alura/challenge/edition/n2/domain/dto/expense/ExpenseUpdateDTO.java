package com.example.alura.challenge.edition.n2.domain.dto.expense;

import java.util.Date;

public record ExpenseUpdateDTO(Long id, String description, Double value, Date date) {
}

package com.example.alura.challenge.edition.n2.domain.dto.summary;

import com.example.alura.challenge.edition.n2.domain.model.Category;

public record SummaryDTO(Double allReceipts, Double allExpenses, Double Balance, Double ExpensesByCategory, Category category) {
}

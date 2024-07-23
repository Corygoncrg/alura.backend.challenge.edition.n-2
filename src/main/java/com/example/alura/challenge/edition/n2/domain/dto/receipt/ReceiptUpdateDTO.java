package com.example.alura.challenge.edition.n2.domain.dto.receipt;

import java.time.LocalDate;

public record ReceiptUpdateDTO(Long id, String description, Double value, LocalDate date) {
}

package com.example.alura.challenge.edition.n2.domain.dto.receipt;

import java.util.Date;

public record ReceiptUpdateDTO(Long id, String description, Double value, Date date) {
}

package com.example.alura.challenge.edition.n2.domain.dto.receipt;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ReceiptRegisterDTO(
        @NotNull String description,
        @NotNull Double value,
        @NotNull Date date
        ) {
}

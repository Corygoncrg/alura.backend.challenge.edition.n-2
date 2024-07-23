package com.example.alura.challenge.edition.n2.domain.dto.receipt;

import com.example.alura.challenge.edition.n2.domain.model.Receipt;

import java.time.LocalDate;

public record ReceiptDTO (Long id, String description, Double value, LocalDate date) {

    public  ReceiptDTO(Receipt receipt){
        this(receipt.getId(), receipt.getDescription(), receipt.getValue(), receipt.getDate());
    }
}

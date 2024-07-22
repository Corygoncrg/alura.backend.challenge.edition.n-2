package com.example.alura.challenge.edition.n2.domain.dto.receipt;

import com.example.alura.challenge.edition.n2.domain.model.Receipt;

import java.util.Date;

public record ReceiptDTO (Long id, String description, Double value, Date date) {

    public  ReceiptDTO(Receipt receipt){
        this(receipt.getId(), receipt.getDescription(), receipt.getValue(), receipt.getDate());
    }
}

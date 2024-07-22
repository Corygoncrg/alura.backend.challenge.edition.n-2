package com.example.alura.challenge.edition.n2.domain.model;

import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table (name = "receipts")
@Entity (name = "Receipt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double value;
    private Date date;
    private Boolean active;


    public Receipt(ReceiptRegisterDTO dto) {
        this.description = dto.description();
        this.value = dto.value();
        this.date = dto.date();
        this.active = true;
    }

    public Receipt(ReceiptDTO dto) {
        this.description = dto.description();
        this.value = dto.value();
        this.date = dto.date();
    }

    public void update(ReceiptUpdateDTO dto){
        if (dto.description() != null){
            this.description = dto.description();
        }
        if (dto.value() != null){
            this.value = dto.value();
        }
        if (dto.date() != null){
            this.date = dto.date();
        }
    }

    public void inactive(){
        this.active = false;
    }
}

package com.example.alura.challenge.edition.n2.domain.model;

import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@SqlResultSetMapping(
        name = "FinancialSummaryMapping",
        columns = {
                @ColumnResult(name = "All_Receipts"),
                @ColumnResult(name = "All_Expenses"),
                @ColumnResult(name = "Final_Balance"),
                @ColumnResult(name = "Expenses_By_Category"),
                @ColumnResult(name = "Category")
        }
)
@Table(name = "expenses")
@Entity(name = "Expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double value;
    private Date date;
    private Boolean active;
    @Enumerated(EnumType.STRING)
     Category category;

    public Expense(ExpenseRegisterDTO dto) {
        this.description = dto.description();
        this.value = dto.value();
        this.date = dto.date();
        this.active = true;
        if (dto.category() == null){
            this.category = Category.OTHER;
        } else {
            this.category = dto.category();
        }
    }

    public Expense(ExpenseDTO dto) {
        this.description = dto.description();
        this.value = dto.value();
        this.date = dto.date();
    }


    public void update(ExpenseUpdateDTO dto){
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
    public void inactive() {
        this.active = false;
    }
}

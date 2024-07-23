package com.example.alura.challenge.edition.n2.domain.service;


import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.model.Expense;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    /**
     * Method to register a new Expense into the database
     * @param dto
     * @param uriBuilder
     * @return ExpenseDetailedDTO
     */
    public ResponseEntity registerExpense(ExpenseRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        LocalDate expenseDate = dto.date();
        int year = expenseDate.getYear();
        int month = expenseDate.getMonthValue();

        var existingExpense = expenseRepository.findByDescriptionAndYearAndMonth(dto.description(), year, month);
        if (existingExpense.isPresent()) {
            return ResponseEntity.badRequest().body("A expense with the same description already exists for the given month.");
        }
        var expense = new Expense(dto);
        expenseRepository.save(expense);
        var uri = uriBuilder.path("/receipt/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseDetailedDTO(expense));
    }

    /**
     * Method that list all expenses, or the ones whose description contains the @RequestParam description
     * @param description
     * @param pageable
     * @return
     */
    public ResponseEntity listOptionalSearch(String description, Pageable pageable) {
        if (description == null) {
            return ResponseEntity.ok(expenseRepository.findAllByActiveTrue(pageable).map(ExpenseDetailedDTO::new));
        } else {
            return ResponseEntity.ok(expenseRepository.findAllByDescriptionContaining(description, pageable).map(ExpenseDetailedDTO::new));
        }
    }

    /**
     * Method to update an existing Receipt in the database
     * @param dto
     * @param uriBuilder
     * @return ExpenseDetailedDTO
     */
    public ResponseEntity updateExpense(ExpenseUpdateDTO dto, UriComponentsBuilder uriBuilder) {
        var expenseDTO = new ExpenseDTO(expenseRepository.getReferenceById(dto.id()));
        LocalDate expenseDate = dto.date();
        int year = expenseDate.getYear();
        int month = expenseDate.getMonthValue();
        var existingExpense = expenseRepository.findByDescriptionAndYearAndMonth(dto.description(), year, month);
        if (existingExpense.isPresent()) {
            return ResponseEntity.badRequest().body("A expense with the same description already exists for the given month.");
        }
        var expense = new Expense(expenseDTO);
        expense.update(dto);
        var uri = uriBuilder.path("/receipt/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseDetailedDTO(expense));
    }
}

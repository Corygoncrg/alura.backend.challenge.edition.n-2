package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import com.example.alura.challenge.edition.n2.domain.service.ExpenseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    @Transactional
    ResponseEntity<?> register (@RequestBody @Valid ExpenseRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        return expenseService.registerExpense(dto, uriBuilder);
    }

    @GetMapping
    ResponseEntity<Page<ExpenseDetailedDTO>> list (@RequestParam(value = "description", required = false) String description, @PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        return expenseService.listOptionalSearch(description, pageable);
    }

    @GetMapping("/{year}/{month}")
    ResponseEntity<Page<ExpenseDetailedDTO>> listByDate (@PageableDefault(size = 10, sort = {"id"}) Pageable pageable, @PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(expenseRepository.findAllByYearAndMonth(pageable, year, month).map(ExpenseDetailedDTO::new));
    }

    @GetMapping("/{id}")
    ResponseEntity<ExpenseDetailedDTO> detail (@PathVariable Long id) {
        var expense = expenseRepository.findById(id).map(ExpenseDetailedDTO::new);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    ResponseEntity<?> update (@RequestBody @Valid ExpenseUpdateDTO dto, UriComponentsBuilder uriBuilder) {
        return expenseService.updateExpense(dto, uriBuilder);
    }

    @DeleteMapping("/{id}")
    @Transactional
    ResponseEntity<Void> delete (@PathVariable Long id){
        return  expenseService.delete(id);
    }
}

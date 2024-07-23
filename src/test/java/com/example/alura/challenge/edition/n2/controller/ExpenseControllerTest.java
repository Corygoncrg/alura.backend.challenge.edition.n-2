package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.model.Category;
import com.example.alura.challenge.edition.n2.domain.model.Expense;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import com.example.alura.challenge.edition.n2.domain.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    ExpenseService expenseService;

    @InjectMocks
    ExpenseController expenseController;

    int year = 2023;
    int month = 1;
    int day = 1;
    LocalDate localDate = LocalDate.of(2023, 1, 1);
    ExpenseRegisterDTO expenseRegisterDTO = new ExpenseRegisterDTO("descr", 69.00, LocalDate.of(year, month, day), null);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return 201 when registering a new Expense")
    void registerExpenseTestCase1() {
        // Given


        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        when(expenseService.registerExpense(any(ExpenseRegisterDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(uriBuilder.path("/expense/1").build().toUri()).build());

        // When
        var responseEntity = expenseController.register(expenseRegisterDTO, uriBuilder);

        // Then
        assertEquals(201, responseEntity.getStatusCode().value());
        assertEquals("/expense/1", Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath());
    }

    @Test
    @DisplayName("Should return 400 when registering a new Expense")
    void registerExpenseTestCase2() {
        // Given

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        when(expenseService.registerExpense(any(ExpenseRegisterDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        // When
        var responseEntity = expenseController.register(new ExpenseRegisterDTO("description", 1.0, localDate, Category.OTHER), uriBuilder);

        // Then
        assertEquals(400, responseEntity.getStatusCode().value());
    }


}
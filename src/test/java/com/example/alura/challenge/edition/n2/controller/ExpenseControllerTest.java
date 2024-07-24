package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.expense.ExpenseUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.model.Category;
import com.example.alura.challenge.edition.n2.domain.model.Expense;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import com.example.alura.challenge.edition.n2.domain.service.ExpenseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    ExpenseDetailedDTO expenseDetailedDTO = new ExpenseDetailedDTO(1L, "descr", 69.00, LocalDate.of(year, month, day), Category.OTHER);
    ExpenseUpdateDTO expenseUpdateDTO = new ExpenseUpdateDTO(1L, "description", 70.00, LocalDate.of(year, month, day), Category.FOOD);


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
    @DisplayName("Should return 400 when failing to register a new Expense")
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

    @Test
    @DisplayName("Should return a list of expenses")
    void listExpensesTest() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<ExpenseDetailedDTO> expenses = List.of(expenseDetailedDTO);
        Page<ExpenseDetailedDTO> page = new PageImpl<>(expenses, pageable, expenses.size());

        when(expenseService.listOptionalSearch(eq("description"), eq(pageable))).thenReturn(ResponseEntity.ok(page));

        // When
        var responseEntity = expenseController.list("description", pageable);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(page, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return a list of expenses by date")
    void listExpensesByDateTest() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Expense> expenses = List.of(new Expense(1L, "descr", 69.00, localDate, true, Category.OTHER));
        Page<Expense> expensePage = new PageImpl<>(expenses, pageable, expenses.size());

        when(expenseRepository.findAllByYearAndMonth(eq(pageable), eq(year), eq(month)))
                .thenReturn(expensePage);

        // When
        var responseEntity = expenseController.listByDate(pageable, year, month);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(expensePage.map(ExpenseDetailedDTO::new), responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return expense details by id")
    void detailExpenseTest() {
        // Given
        ExpenseDetailedDTO expenseDetailedDTO = new ExpenseDetailedDTO(1L, "descr", 69.00, localDate, Category.OTHER);
        when(expenseRepository.findById(eq(1L))).thenReturn(Optional.of(new Expense(1L, "descr", 69.00, localDate, true, Category.OTHER)));

        // When
        var responseEntity = expenseController.detail(1L);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(expenseDetailedDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should update an expense")
    void updateExpenseTest() {
        // Given
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        when(expenseService.updateExpense(eq(expenseUpdateDTO), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.ok().build());

        // When
        var responseEntity = expenseController.update(expenseUpdateDTO, uriBuilder);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("Should delete an expense")
    void deleteExpenseTest() {
        // Given
        when(expenseService.delete(eq(1L))).thenReturn(ResponseEntity.noContent().build());

        // When
        var responseEntity = expenseController.delete(1L);

        // Then
        assertEquals(204, responseEntity.getStatusCode().value());
    }

}
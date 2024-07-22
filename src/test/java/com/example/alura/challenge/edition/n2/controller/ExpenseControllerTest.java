package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.model.Expense;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import com.example.alura.challenge.edition.n2.domain.service.ExpenseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    ExpenseService expenseService;

    @InjectMocks
    ExpenseController expenseController;


    @Test
    @DisplayName("Should return 200 when listing expenses")
    void listExpenses() throws Exception {
        Pageable pageable = Pageable.unpaged();
        expenseRepository.save(new Expense());
        when(expenseRepository.findAllByActiveTrue(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));
//
        var response = expenseController.list(null, pageable);
//
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.toString())
        );
    }


}
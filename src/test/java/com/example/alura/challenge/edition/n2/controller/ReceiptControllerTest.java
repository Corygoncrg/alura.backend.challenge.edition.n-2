package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.model.Receipt;
import com.example.alura.challenge.edition.n2.domain.repository.ReceiptRepository;
import com.example.alura.challenge.edition.n2.domain.service.ReceiptService;
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
class ReceiptControllerTest {
    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    ReceiptService receiptService;

    @InjectMocks
    ReceiptController receiptController;

    int year = 2023;
    int month = 1;
    LocalDate localDate = LocalDate.of(2023, 1, 1);
    ReceiptRegisterDTO receiptRegisterDTO = new ReceiptRegisterDTO("descr", 69.00, localDate);
    ReceiptDetailedDTO receiptDetailedDTO = new ReceiptDetailedDTO(1L, "descr", 69.00, localDate);
    ReceiptUpdateDTO receiptUpdateDTO = new ReceiptUpdateDTO(1L, "description", 70.00, LocalDate.of(2023, 2, 1));


    @Test
    @DisplayName("Should return 201 when registering a new receipt")
    void registerExpenseTestCase1() {
        // Given


        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        when(receiptService.registerReceipt(any(ReceiptRegisterDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.created(uriBuilder.path("/expense/1").build().toUri()).build());

        // When
        var responseEntity = receiptController.register(receiptRegisterDTO, uriBuilder);

        // Then
        assertEquals(201, responseEntity.getStatusCode().value());
        assertEquals("/expense/1", Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath());
    }

    @Test
    @DisplayName("Should return 400 when failing to register a new receipt")
    void registerExpenseTestCase2() {
        // Given

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        when(receiptService.registerReceipt(any(ReceiptRegisterDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        // When
        var responseEntity = receiptService.registerReceipt(new ReceiptRegisterDTO("description", 1.0, localDate), uriBuilder);

        // Then
        assertEquals(400, responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("Should return a list of receipts")
    void listExpensesTest() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<ReceiptDetailedDTO> expenses = List.of(receiptDetailedDTO);
        Page<ReceiptDetailedDTO> page = new PageImpl<>(expenses, pageable, expenses.size());

        when(receiptService.listOptionalSearch(eq("description"), eq(pageable))).thenReturn(ResponseEntity.ok(page));

        // When
        var responseEntity = receiptController.list("description", pageable);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(page, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return a list of receipt by date")
    void listExpensesByDateTest() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Receipt> receipts = List.of(new Receipt(1L, "descr", 69.00, localDate, true));
        Page<Receipt> expensePage = new PageImpl<>(receipts, pageable, receipts.size());

        when(receiptRepository.findAllByYearAndMonth(eq(pageable), eq(year), eq(month)))
                .thenReturn(expensePage);

        // When
        var responseEntity = receiptController.listByDate(pageable, year, month);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(expensePage.map(ReceiptDetailedDTO::new), responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return receipt details by id")
    void detailExpenseTest() {
        // Given
        when(receiptRepository.findById(eq(1L))).thenReturn(Optional.of(new Receipt(1L, "descr", 69.00, localDate, true)));

        // When
        var responseEntity = receiptController.detail(1L);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(receiptDetailedDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should update an receipt")
    void updateExpenseTest() {
        // Given
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        when(receiptService.updateReceipt(eq(receiptUpdateDTO), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.ok().build());

        // When
        var responseEntity = receiptController.update(receiptUpdateDTO, uriBuilder);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("Should delete an receipt")
    void deleteExpenseTest() {
        // Given
        when(receiptService.delete(eq(1L))).thenReturn(ResponseEntity.noContent().build());

        // When
        var responseEntity = receiptController.delete(1L);

        // Then
        assertEquals(204, responseEntity.getStatusCode().value());
    }

}
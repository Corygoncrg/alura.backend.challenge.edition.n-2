package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.summary.SummaryDTO;
import com.example.alura.challenge.edition.n2.domain.model.Category;
import com.example.alura.challenge.edition.n2.domain.service.FinancialService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummaryControllerTest {
    @Mock
    FinancialService financialService;

    @InjectMocks
    SummaryController summaryController;

    @Test
    @DisplayName("Should return a financial summary for the given year and month")
    void monthlySummaryTest() {
        // Given
        int year = 2023;
        int month = 1;
        List<SummaryDTO> summaryList = List.of(
                new SummaryDTO(1000.0, 500.0, 500.0,  100.0,Category.OTHER),
                new SummaryDTO(1000.0, 200.0, 800.0, 400.0 ,Category.FOOD)
        );

        when(financialService.getFinancialSummary(anyInt(), anyInt())).thenReturn(summaryList);

        // When
        ResponseEntity<List<SummaryDTO>> responseEntity = summaryController.monthlysummary(year, month);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(summaryList, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return an empty list when there is no financial summary for the given year and month")
    void monthlySummaryEmptyTest() {
        // Given
        int year = 2023;
        int month = 1;
        List<SummaryDTO> emptySummaryList = List.of();

        when(financialService.getFinancialSummary(anyInt(), anyInt())).thenReturn(emptySummaryList);

        // When
        ResponseEntity<List<SummaryDTO>> responseEntity = summaryController.monthlysummary(year, month);

        // Then
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(emptySummaryList, responseEntity.getBody());
    }

}
package com.example.alura.challenge.edition.n2.domain.repository;

import com.example.alura.challenge.edition.n2.domain.dto.summary.SummaryDTO;

import java.util.List;

public interface CustomRepository {
    List<SummaryDTO> getFinancialSummary(int year, int month);
}

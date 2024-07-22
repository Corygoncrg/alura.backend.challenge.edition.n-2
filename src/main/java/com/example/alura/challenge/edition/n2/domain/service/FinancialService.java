package com.example.alura.challenge.edition.n2.domain.service;

import com.example.alura.challenge.edition.n2.domain.dto.summary.SummaryDTO;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import com.example.alura.challenge.edition.n2.domain.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FinancialService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    public List<SummaryDTO> getFinancialSummary(int year, int month) {
        return expenseRepository.getFinancialSummary(year, month);
    }
}

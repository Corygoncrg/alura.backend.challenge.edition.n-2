package com.example.alura.challenge.edition.n2.domain.service;

import com.example.alura.challenge.edition.n2.domain.dto.summary.SummaryDTO;
import com.example.alura.challenge.edition.n2.domain.repository.ExpenseRepository;
import com.example.alura.challenge.edition.n2.domain.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ReceiptRepository receiptRepository;


    /**
     * Method to get the financial summary of a month by specifying the year and month of the search
     * @param year int value of year
     * @param month int value of month
     * @return List<SummaryDTO>
     */
    public List<SummaryDTO> getFinancialSummary(int year, int month) {
        return expenseRepository.getFinancialSummary(year, month);
    }
}

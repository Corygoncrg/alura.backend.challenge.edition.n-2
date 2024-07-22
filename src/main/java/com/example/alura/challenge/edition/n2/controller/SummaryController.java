package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.summary.SummaryDTO;
import com.example.alura.challenge.edition.n2.domain.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("summary")
public class SummaryController {

    @Autowired
private FinancialService financialService;

    @GetMapping("/{year}/{month}")
    ResponseEntity<List<SummaryDTO>> monthlysummary(@PathVariable int year, @PathVariable int month){
        return ResponseEntity.ok(financialService.getFinancialSummary(year, month));
    }


}

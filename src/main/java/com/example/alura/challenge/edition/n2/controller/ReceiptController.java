package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.model.Receipt;
import com.example.alura.challenge.edition.n2.domain.repository.ReceiptRepository;
import com.example.alura.challenge.edition.n2.domain.service.ReceiptService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("receipt")
public class ReceiptController {

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    ReceiptService receiptService;

    @PostMapping
    @Transactional
    ResponseEntity register (@RequestBody @Valid ReceiptRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        return receiptService.registerReceipt(dto, uriBuilder);
    }


    @GetMapping
    ResponseEntity list (@RequestParam(value = "description", required = false) String description, @PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        return receiptService.listOptionalSearch(description, pageable);
    }

    @GetMapping("/{year}/{month}")
    ResponseEntity listByDate (@PageableDefault(size = 10, sort = {"id"}) Pageable pageable, @PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(receiptRepository.findAllByYearAndMonth(pageable, year, month).map(ReceiptDetailedDTO::new));
    }


    @PutMapping
    @Transactional
    ResponseEntity update (@RequestBody @Valid ReceiptUpdateDTO dto, UriComponentsBuilder uriBuilder) {
        return receiptService.updateReceipt(dto, uriBuilder);
    }

    @DeleteMapping("/{id}")
    @Transactional
    ResponseEntity delete (@PathVariable Long id){
        var receipt = receiptRepository.getReferenceById(id);
        receipt.inactive();
        return ResponseEntity.noContent().build();
    }


}

package com.example.alura.challenge.edition.n2.domain.service;

import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptDetailedDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptRegisterDTO;
import com.example.alura.challenge.edition.n2.domain.dto.receipt.ReceiptUpdateDTO;
import com.example.alura.challenge.edition.n2.domain.model.Receipt;
import com.example.alura.challenge.edition.n2.domain.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class ReceiptService {

    @Autowired
    ReceiptRepository receiptRepository;

    /**
     * Method to register a new Receipt into the database
     * @param dto Data transfer object containing the details for a new Receipt
     * @param uriBuilder URI components builder for creating the URI of the created resource
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> registerReceipt(ReceiptRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        LocalDate receiptDate = dto.date();
        int year = receiptDate.getYear();
        int month = receiptDate.getMonthValue();

        var existingReceipt = receiptRepository.findByDescriptionAndYearAndMonth(dto.description(), year, month);
        if (existingReceipt.isPresent()) {
            return ResponseEntity.badRequest().body("A receipt with the same description already exists for the given month.");
        }
        var receipt = new Receipt(dto);
        receiptRepository.save(receipt);
        var uri = uriBuilder.path("/receipt/{id}").buildAndExpand(receipt.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReceiptDetailedDTO(receipt));
    }

    /**
     * Method that list all receipts, or the ones whose description contains the @RequestParam description
     * @param description Optional description in which the repository will use to search for in the database
     * @param pageable Pageable object for pagination information
     * @return ResponseEntity<Page<ReceiptDetailedDTO>>
     */
    public ResponseEntity<Page<ReceiptDetailedDTO>> listOptionalSearch(String description, Pageable pageable) {
        if (description == null) {
            return ResponseEntity.ok(receiptRepository.findAllByActiveTrue(pageable).map(ReceiptDetailedDTO::new));
        } else {
         return ResponseEntity.ok(receiptRepository.findAllByDescriptionContaining(description, pageable).map(ReceiptDetailedDTO::new));
        }
    }

    /**
     * Method to update an existing Receipt in the database
     * @param dto Data transfer object containing updated receipt details
     * @param uriBuilder URI components builder for creating the URI of the updated resource
     * @return ReceiptDetailedDTO
     */
    public ResponseEntity<?> updateReceipt(ReceiptUpdateDTO dto, UriComponentsBuilder uriBuilder) {
        var receiptDTO = new ReceiptDTO(receiptRepository.getReferenceById(dto.id()));
        LocalDate receiptDate = dto.date();
        int year = receiptDate.getYear();
        int month = receiptDate.getMonthValue();
        var existingReceipt = receiptRepository.findByDescriptionAndYearAndMonth(dto.description(), year, month);
        if (existingReceipt.isPresent()) {
            return ResponseEntity.badRequest().body("A receipt with the same description already exists for the given month.");
        }
        var receipt = new Receipt(receiptDTO);
        receipt.update(dto);
        var uri = uriBuilder.path("/receipt/{id}").buildAndExpand(receipt.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReceiptDetailedDTO(receipt));
    }


    /**
     * Method to turn the active boolean to false
     * @param id Long id for the repository to search for in the database
     * @return ResponseEntity<Void>
     */
    public ResponseEntity<Void> delete(Long id){
        var receipt = receiptRepository.getReferenceById(id);
        receipt.inactive();
        return ResponseEntity.noContent().build();
    }
}

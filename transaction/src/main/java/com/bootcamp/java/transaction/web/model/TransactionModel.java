package com.bootcamp.java.transaction.web.model;

import com.bootcamp.java.transaction.domain.ClientDocument;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {
    private String id;
    @NotBlank(message = "Product Code Source cannot be null or empty")
    private String productCode;
    private String creditNumber;
    private String accountNumber;
    private String cardNumber;
    @NotBlank(message = "Product Code Target cannot be null or empty")
    private String productCodeTarget;
    private String accountNumberTarget;
    private String cardNumberTarget;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    @NotNull
    private Float transactionAmount;
    private Float transactionCommission;
    @NotBlank(message = "Transaction Type cannot be null or empty")
    private String transactionType;
    private ClientDocumentModel client;
    private ClientDocumentModel clientTarget;
}

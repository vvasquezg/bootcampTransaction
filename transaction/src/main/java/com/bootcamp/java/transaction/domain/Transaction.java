package com.bootcamp.java.transaction.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "transaction")
public class Transaction {
    @Id
    private String id;
    @NotNull
    private String productCode;
    private String creditNumber;
    private String accountNumber;
    private String cardNumber;
    @NotNull
    private String productCodeTarget;
    private String accountNumberTarget;
    private String cardNumberTarget;
    @NotNull
    private LocalDate transactionDate;
    @NotNull
    private Float transactionAmount;
    @NotNull
    private Float transactionCommission;
    @NotNull
    private String transactionType;
    private ClientDocument client;
    private ClientDocument clientTarget;
}

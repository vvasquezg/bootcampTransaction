package com.bootcamp.java.transaction.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardModel {
    @JsonIgnore
    private String id;
    private String productCode;
    private String cardNumber;
    private String cardCompany;
    private Float amountOwed;
    private Float amountAvailable;
    private Float amountMax;
    private Integer payDay;
    private LocalDate openingDate;
    private ClientDocumentModel client;
    private Boolean active;
}

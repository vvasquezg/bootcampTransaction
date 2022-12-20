package com.bootcamp.java.transaction.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    @JsonIgnore
    private String id;
    private String productCode;
    private String accountNumber;
    private String cardNumber;
    private String cardCompany;
    private String bankName;
    private Float amountAvailable;
    private LocalDate openingDate;
    private ClientDocumentModel client;
    private List<ClientDocumentModel> holder;
    private List<ClientDocumentModel> signer;
    private Boolean active;
}

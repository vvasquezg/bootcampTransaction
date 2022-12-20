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
public class CreditModel {
    @JsonIgnore
    private String id;
    private String productCode;
    private LocalDate creditDate;
    private String creditNumber;
    private Float amountRequested;
    private Float accountPending;
    private Float monthlyFee;
    private Integer payDay;
    private ClientDocumentModel client;
    private Boolean active;
}

package com.bootcamp.java.transaction.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTypeModel {
    @JsonIgnore
    private String id;
    private String productCode;
    private String code;
    private String name;
    private Boolean isSum;
    private Boolean active;
}

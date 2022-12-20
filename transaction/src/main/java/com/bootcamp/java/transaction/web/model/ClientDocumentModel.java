package com.bootcamp.java.transaction.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDocumentModel {
    private String identityDocumentType;
    private String identityDocumentNumber;
}

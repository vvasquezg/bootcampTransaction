package com.bootcamp.java.transaction.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "code" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "transactionType")
public class TransactionType {
    @Id
    private String id;
    private String productCode;
    private String code;
    private String name;
    private Boolean isSum;
    private Boolean active;
}

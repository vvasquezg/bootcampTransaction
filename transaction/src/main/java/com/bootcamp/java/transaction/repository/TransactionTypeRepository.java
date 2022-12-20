package com.bootcamp.java.transaction.repository;

import com.bootcamp.java.transaction.domain.TransactionType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionTypeRepository extends ReactiveMongoRepository<TransactionType, String> {
    Mono<TransactionType> findTopByCodeAndProductCodeAndActive(String code, String productCode, Boolean active);
}

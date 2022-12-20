package com.bootcamp.java.transaction.service;

import com.bootcamp.java.transaction.domain.TransactionType;
import com.bootcamp.java.transaction.repository.TransactionTypeRepository;
import com.bootcamp.java.transaction.web.mapper.TransactionTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionTypeService {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionTypeMapper transactionTypeMapper;

    public Flux<TransactionType> findAll(){
        log.debug("findAll executed");
        return transactionTypeRepository.findAll();
    }

    public Mono<TransactionType> findById(String id){
        log.debug("findById executed {}", id);
        return transactionTypeRepository.findById(id);
    }

    public Mono<TransactionType> findByCodeAndProductCode(String code, String productCode){
        log.debug("findByCodeAndProductCode executed {} - {}", code, productCode);
        return transactionTypeRepository.findTopByCodeAndProductCodeAndActive(code, productCode, true);
    }

    public Mono<TransactionType> create(TransactionType transactionType){
        log.debug("create executed {}", transactionType);
        return transactionTypeRepository.save(transactionType);
    }

    public Mono<TransactionType> update(String id, TransactionType transactionType) {
        log.debug("update executed {}:{}", id, transactionType);
        return transactionTypeRepository.findById(id)
                .flatMap(dbTransactionType -> {
                    transactionTypeMapper.update(dbTransactionType, transactionType);
                    return transactionTypeRepository.save(dbTransactionType);
                });
    }

    public Mono<TransactionType> delete(String id){
        log.debug("delete executed {}", id);
        return transactionTypeRepository.findById(id)
                .flatMap(existingProductType -> transactionTypeRepository.delete(existingProductType)
                        .then(Mono.just(existingProductType)));
    }
}

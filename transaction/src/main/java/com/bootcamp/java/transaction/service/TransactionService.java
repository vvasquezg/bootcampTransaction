package com.bootcamp.java.transaction.service;

import com.bootcamp.java.transaction.domain.Transaction;
import com.bootcamp.java.transaction.repository.TransactionRepository;
import com.bootcamp.java.transaction.service.exception.InvalidClientException;
import com.bootcamp.java.transaction.web.mapper.TransactionMapper;
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
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private CommonService commonService;

    public Flux<Transaction> findAll(){
        log.debug("findAll executed");
        return transactionRepository.findAll();
    }

    public Mono<Transaction> findById(String id){
        log.debug("findById executed {}", id);
        return transactionRepository.findById(id);
    }


    public Mono<Transaction> deposit(Transaction transaction) {
        log.debug("deposit executed {}", transaction);
        return commonService.getClientByDocument(transaction.getClient())
                .switchIfEmpty(Mono.error(new InvalidClientException()))
                .flatMap(clientModel -> transactionTypeService.findByCodeAndProductCode(transaction.getTransactionType(), transaction.getProductCode())
                        .switchIfEmpty(Mono.error(new Exception("Transaction type not configured for this product")))
                        .flatMap(transactionType -> {
                            commonService.getByAccountNumber(transaction.getAccountNumber())
                                    .switchIfEmpty(Mono.error(new Exception("Account not valid")))
                                    .flatMap(accountModel -> {
                                        accountModel.setAmountAvailable(accountModel.getAmountAvailable() + transaction.getTransactionAmount());
                                        return commonService.updateByAccountNumber(accountModel);
                                    });
                            return transactionRepository.save(transaction);
                        }));
    }

    public Mono<Transaction> withdrawal(Transaction transaction) {
        log.debug("deposit executed {}", transaction);
        return commonService.getClientByDocument(transaction.getClient())
                .switchIfEmpty(Mono.error(new InvalidClientException()))
                .flatMap(clientModel -> transactionTypeService.findByCodeAndProductCode(transaction.getTransactionType(), transaction.getProductCode())
                        .switchIfEmpty(Mono.error(new Exception("Transaction type not configured for this product")))
                        .flatMap(transactionType -> {
                            commonService.getByAccountNumber(transaction.getAccountNumber())
                                    .switchIfEmpty(Mono.error(new Exception("Account not valid")))
                                    .flatMap(accountModel -> {
                                        accountModel.setAmountAvailable(accountModel.getAmountAvailable() - transaction.getTransactionAmount());
                                        return commonService.updateByAccountNumber(accountModel);
                                    });
                            return transactionRepository.save(transaction);
                        }));
    }

    public Mono<Transaction> paymentCredit(Transaction transaction) {
        log.debug("paymentCredit executed {}", transaction);
        return commonService.getClientByDocument(transaction.getClient())
                .switchIfEmpty(Mono.error(new InvalidClientException()))
                .flatMap(clientModel -> transactionTypeService.findByCodeAndProductCode(transaction.getTransactionType(), transaction.getProductCode())
                        .switchIfEmpty(Mono.error(new Exception("Transaction type not configured for this product")))
                        .flatMap(transactionType -> {
                            commonService.getByCreditNumber(transaction.getCreditNumber())
                                    .switchIfEmpty(Mono.error(new Exception("Credit not valid")))
                                    .flatMap(creditModel -> {
                                        creditModel.setAccountPending(creditModel.getAccountPending() - transaction.getTransactionAmount());
                                        return commonService.updateByCreditNumber(creditModel);
                                    });
                            return transactionRepository.save(transaction);
                        }));
    }

    public Mono<Transaction> paymentCard(Transaction transaction) {
        log.debug("paymentCard executed {}", transaction);
        return commonService.getClientByDocument(transaction.getClient())
                .switchIfEmpty(Mono.error(new InvalidClientException()))
                .flatMap(clientModel -> transactionTypeService.findByCodeAndProductCode(transaction.getTransactionType(), transaction.getProductCode())
                        .switchIfEmpty(Mono.error(new Exception("Transaction type not configured for this product")))
                        .flatMap(transactionType -> {
                            commonService.getByCardNumber(transaction.getCardNumber())
                                    .switchIfEmpty(Mono.error(new Exception("Card not valid")))
                                    .flatMap(cardModel -> {
                                        cardModel.setAmountAvailable(cardModel.getAmountAvailable() + transaction.getTransactionAmount());
                                        cardModel.setAmountOwed(cardModel.getAmountOwed() - transaction.getTransactionAmount());
                                        return commonService.updateByCardNumber(cardModel);
                                    });
                            return transactionRepository.save(transaction);
                        }));
    }

    public Mono<Transaction> consumeCard(Transaction transaction) {
        log.debug("consumeCard executed {}", transaction);
        return commonService.getClientByDocument(transaction.getClient())
                .switchIfEmpty(Mono.error(new InvalidClientException()))
                .flatMap(clientModel -> transactionTypeService.findByCodeAndProductCode(transaction.getTransactionType(), transaction.getProductCode())
                        .switchIfEmpty(Mono.error(new Exception("Transaction type not configured for this product")))
                        .flatMap(transactionType -> {
                            commonService.getByCardNumber(transaction.getCardNumber())
                                    .switchIfEmpty(Mono.error(new Exception("Card not valid")))
                                    .flatMap(cardModel -> {
                                        cardModel.setAmountAvailable(cardModel.getAmountAvailable() - transaction.getTransactionAmount());
                                        cardModel.setAmountOwed(cardModel.getAmountOwed() + transaction.getTransactionAmount());
                                        return commonService.updateByCardNumber(cardModel);
                                    });
                            return transactionRepository.save(transaction);
                        }));
    }

}

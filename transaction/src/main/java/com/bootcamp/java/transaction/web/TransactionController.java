package com.bootcamp.java.transaction.web;

import com.bootcamp.java.transaction.domain.Transaction;
import com.bootcamp.java.transaction.service.TransactionService;
import com.bootcamp.java.transaction.web.mapper.TransactionMapper;
import com.bootcamp.java.transaction.web.model.TransactionModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<TransactionModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(transactionService.findAll()
                        .map(client -> transactionMapper.entityToModel(client))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<Transaction> response = transactionService.findById(id);
        return response
                .map(customer -> transactionMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/deposit")
    public Mono<ResponseEntity<TransactionModel>> deposit(@Valid @RequestBody TransactionModel request){
        log.info("deposit executed {}", request);
        return transactionService.deposit(transactionMapper.modelToEntity(request))
                .map(client -> transactionMapper.entityToModel(client))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "transaction", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/withdrawal")
    public Mono<ResponseEntity<TransactionModel>> withdrawal(@Valid @RequestBody TransactionModel request){
        log.info("withdrawal executed {}", request);
        return transactionService.withdrawal(transactionMapper.modelToEntity(request))
                .map(client -> transactionMapper.entityToModel(client))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "transaction", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/paymentCredit")
    public Mono<ResponseEntity<TransactionModel>> paymentCredit(@Valid @RequestBody TransactionModel request){
        log.info("paymentCredit executed {}", request);
        return transactionService.paymentCredit(transactionMapper.modelToEntity(request))
                .map(client -> transactionMapper.entityToModel(client))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "transaction", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/paymentCard")
    public Mono<ResponseEntity<TransactionModel>> paymentCard(@Valid @RequestBody TransactionModel request){
        log.info("paymentCard executed {}", request);
        return transactionService.paymentCard(transactionMapper.modelToEntity(request))
                .map(client -> transactionMapper.entityToModel(client))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "transaction", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}

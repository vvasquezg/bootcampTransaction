package com.bootcamp.java.transaction.web;

import com.bootcamp.java.transaction.domain.TransactionType;
import com.bootcamp.java.transaction.service.TransactionTypeService;
import com.bootcamp.java.transaction.web.mapper.TransactionTypeMapper;
import com.bootcamp.java.transaction.web.model.TransactionTypeModel;
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
@RequestMapping("/v1/transactionType")
public class TransactionTypeController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionTypeMapper transactionTypeMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<TransactionTypeModel>>> getAll(){
        log.info("getAll executed");

        return Mono.just(ResponseEntity.ok()
                .body(transactionTypeService.findAll()
                        .map(transactionType -> transactionTypeMapper.entityToModel(transactionType))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionTypeModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<TransactionType> response = transactionTypeService.findById(id);
        return response
                .map(transactionType -> transactionTypeMapper.entityToModel(transactionType))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByCodeAndProductCode/{code}/{productCode}")
    public Mono<ResponseEntity<TransactionTypeModel>> getByCodeAndProductCode(@PathVariable String code, @PathVariable String productCode){
        log.info("getByCodeAndProductCode executed {} - {}", code, productCode);
        Mono<TransactionType> response = transactionTypeService.findByCodeAndProductCode(code, productCode);
        return response
                .map(customer -> transactionTypeMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TransactionTypeModel>> create(@Valid @RequestBody TransactionTypeModel request){
        log.info("create executed {}", request);
        return transactionTypeService.create(transactionTypeMapper.modelToEntity(request))
                .map(customer -> transactionTypeMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "productType", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TransactionTypeModel>> updateById(@PathVariable String id, @Valid @RequestBody TransactionTypeModel request){
        log.info("updateById executed {}:{}", id, request);
        return transactionTypeService.update(id, transactionTypeMapper.modelToEntity(request))
                .map(customer -> transactionTypeMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "customer", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return transactionTypeService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

package com.bootcamp.java.transaction.web.mapper;

import com.bootcamp.java.transaction.domain.Transaction;
import com.bootcamp.java.transaction.web.model.TransactionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction modelToEntity(TransactionModel model);
    TransactionModel entityToModel(Transaction event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Transaction entity, Transaction updateEntity);
}

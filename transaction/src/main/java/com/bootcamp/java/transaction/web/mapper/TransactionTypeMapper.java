package com.bootcamp.java.transaction.web.mapper;

import com.bootcamp.java.transaction.domain.TransactionType;
import com.bootcamp.java.transaction.web.model.TransactionTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {
    TransactionType modelToEntity(TransactionTypeModel model);
    TransactionTypeModel entityToModel(TransactionType event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget TransactionType entity, TransactionType updateEntity);
}

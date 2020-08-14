package com.example.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.dto.EventChangeRequest;
import com.example.dto.EventDto;
import com.example.persistence.pojo.EventEntity;

@Mapper(componentModel = "spring")
public interface EventMapper {

    List<EventDto> entitiesToModels(List<EventEntity> entities);

    EventDto entityToModel(EventEntity entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    EventEntity changeRequestToEntity(EventChangeRequest request);
}

package com.example.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.EventChangeRequest;
import com.example.dto.EventDto;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.EventMapper;
import com.example.persistence.pojo.EventEntity;
import com.example.persistence.pojo.enums.EventTypeEnum;
import com.example.persistence.repository.EventRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final EventMapper mapper;

    public EventDto createEvent(EventChangeRequest request) {
        var entity = mapper.changeRequestToEntity(request);
        return mapper.entityToModel(repository.save(entity));
    }

    public EventDto changeEvent(Long id, EventChangeRequest request) {
        var eventEntity = getEventEntity(id);

        eventEntity.setName(request.getName());
        eventEntity.setDescription(request.getDescription());
        eventEntity.setAddress(request.getAddress());
        eventEntity.setPrice(request.getPrice());
        eventEntity.setType(request.getType());
        eventEntity.setHappensOn(request.getHappensOn());

        eventEntity = repository.save(eventEntity);

        return mapper.entityToModel(eventEntity);
    }

    public EventDto getEventById(Long id) {
        var eventEntity = getEventEntity(id);

        return mapper.entityToModel(eventEntity);
    }

    public List<EventDto> getEventsOfType(EventTypeEnum type, int page, int size) {
        var events = repository.findAllByTypeOrderByHappensOnDesc(type, PageRequest.of(page, size));
        return mapper.entitiesToModels(events);
    }

    @Transactional // to remove in the same transaction and avoid additional select requests
    public void deleteEvent(Long id) {
        var event = getEventEntity(id);
        repository.delete(event);
    }

    private EventEntity getEventEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event not found for id=%s", id)));
    }
}

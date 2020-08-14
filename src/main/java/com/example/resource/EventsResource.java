package com.example.resource;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EventChangeRequest;
import com.example.dto.EventDto;
import com.example.persistence.pojo.enums.EventTypeEnum;
import com.example.service.EventService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventsResource {

    private final EventService service;

    @GetMapping
    public List<EventDto> getAllByType(@RequestParam EventTypeEnum type, @RequestParam Integer page, @RequestParam Integer size) {
        return service.getEventsOfType(type, page, size);
    }

    @GetMapping("/{id}")
    public EventDto getById(@PathVariable Long id) {
        return service.getEventById(id);
    }

    @PostMapping
    public EventDto createEvent(@RequestBody @Validated EventChangeRequest request) {
        return service.createEvent(request);
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(@PathVariable Long id, @RequestBody @Validated EventChangeRequest request) {
        return service.changeEvent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        service.deleteEvent(id);
    }
}
package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dto.EventChangeRequest;
import com.example.dto.EventDto;
import com.example.exception.EntityNotFoundException;
import com.example.persistence.pojo.EventEntity;
import com.example.persistence.pojo.enums.EventTypeEnum;
import com.example.persistence.repository.EventRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceIntTest {

    @Autowired
    private EventService service;

    @Autowired
    private EventRepository repository;

    @Test
    public void createNewEvent() {
        // given
        var req = EventChangeRequest.builder()
                .name("createNewEvent name")
                .description("createNewEvent description")
                .address("createNewEvent address")
                .price(new BigDecimal(123))
                .happensOn(LocalDateTime.now())
                .type(EventTypeEnum.CONCERT)
                .build();

        // when
        var response = service.createEvent(req);

        // then
        assertResponseFieldsEqualsToReq(response, req);

        var byId = repository.findById(response.getId());
        assertThat(byId).isPresent();

        assertEntityEqualsToResponse(byId.get(), response);
    }

    @Test
    public void updateExistingEvent() {
        // given
        var existingEntity = repository.save(EventEntity.builder()
                .name("updateExistingEvent name")
                .description("updateExistingEvent description")
                .address("updateExistingEvent address")
                .price(new BigDecimal(321))
                .happensOn(LocalDateTime.now())
                .type(EventTypeEnum.FILM)
                .build());

        // when
        var eventChangeRequest = EventChangeRequest.builder()
                .name("updateExistingEvent name updated")
                .description("updateExistingEvent name updated")
                .address("updateExistingEvent name updated")
                .price(new BigDecimal(33333))
                .happensOn(LocalDateTime.now().minusDays(3))
                .type(EventTypeEnum.CONCERT)
                .build();

        var response = service.changeEvent(existingEntity.getId(), eventChangeRequest);
        assertResponseFieldsEqualsToReq(response, eventChangeRequest);

        var byId = repository.findById(response.getId());
        assertEntityEqualsToResponse(byId.get(), response);
    }

    @Test
    public void getEventById() {
        // given
        var existingEntity = repository.save(EventEntity.builder()
                .name("getEventById name")
                .description("getEventById description")
                .address("getEventById address")
                .price(new BigDecimal(321))
                .happensOn(LocalDateTime.now())
                .type(EventTypeEnum.FILM)
                .build());

        // when
        var response = service.getEventById(existingEntity.getId());

        // then
        assertEntityEqualsToResponse(existingEntity, response);
    }

    @Test
    public void deleteEventById() {
        // given
        var existingEntity = repository.save(EventEntity.builder()
                .name("deleteEventById name")
                .description("deleteEventById description")
                .address("deleteEventById address")
                .price(new BigDecimal(321))
                .happensOn(LocalDateTime.now())
                .type(EventTypeEnum.FILM)
                .build());

        // when
        service.deleteEvent(existingEntity.getId());

        // then
        assertThat(repository.findById(existingEntity.getId()).isEmpty());
    }

    @Test
    public void getNonExistingEvent() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.getEventById(3212312L))
                .withMessage("Event not found for id=3212312");
    }

    @Test
    public void getEventsOfType() {
        // given
        var film1 = repository.save(EventEntity.builder()
                .name("getEventsOfType film1")
                .address("getEventsOfType address")
                .price(new BigDecimal(321))
                .happensOn(LocalDateTime.now().minusDays(3))
                .type(EventTypeEnum.FILM)
                .build());
        var film2 = repository.save(EventEntity.builder()
                .name("getEventsOfType film2")
                .address("getEventsOfType address")
                .price(new BigDecimal(321))
                .happensOn(LocalDateTime.now())
                .type(EventTypeEnum.FILM)
                .build());
        var performance = repository.save(EventEntity.builder()
                .name("getEventsOfType performance")
                .address("getEventsOfType address")
                .price(new BigDecimal(321))
                .happensOn(LocalDateTime.now())
                .type(EventTypeEnum.PERFORMANCE)
                .build());

        // when
        var response = service.getEventsOfType(EventTypeEnum.FILM, 0, 10);

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getId()).isEqualTo(film2.getId()); // test sorting
        assertThat(response.get(1).getId()).isEqualTo(film1.getId());
    }

    private void assertEntityEqualsToResponse(EventEntity byId, EventDto response) {
        assertThat(byId.getName()).isEqualTo(response.getName());
        assertThat(byId.getDescription()).isEqualTo(response.getDescription());
        assertThat(byId.getAddress()).isEqualTo(response.getAddress());
        assertThat(byId.getPrice()).isEqualTo(response.getPrice());
        assertThat(byId.getHappensOn()).isEqualTo(response.getHappensOn());
        assertThat(byId.getType()).isEqualTo(response.getType());
        assertThat(byId.getCreatedDate()).isEqualTo(response.getCreatedDate());
        assertThat(byId.getLastModifiedDate()).isEqualTo(response.getLastModifiedDate());
    }

    private void assertResponseFieldsEqualsToReq(EventDto response, EventChangeRequest req) {
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo(req.getName());
        assertThat(response.getDescription()).isEqualTo(req.getDescription());
        assertThat(response.getAddress()).isEqualTo(req.getAddress());
        assertThat(response.getPrice()).isEqualTo(req.getPrice());
        assertThat(response.getHappensOn()).isEqualTo(req.getHappensOn());
        assertThat(response.getType()).isEqualTo(req.getType());
        assertThat(response.getCreatedDate()).isNotNull();
        assertThat(response.getLastModifiedDate()).isNotNull();
    }
}

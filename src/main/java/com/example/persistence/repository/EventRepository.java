package com.example.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.pojo.EventEntity;
import com.example.persistence.pojo.enums.EventTypeEnum;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByTypeOrderByHappensOnDesc(EventTypeEnum type, Pageable pageable);
}

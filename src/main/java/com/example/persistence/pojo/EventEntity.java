package com.example.persistence.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.persistence.pojo.enums.EventTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "event")
public class EventEntity extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "event_id_seq_generator", sequenceName = "event_id_seq", allocationSize = 10)
    @GeneratedValue(generator = "event_id_seq_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;
    private String address;
    private LocalDateTime happensOn;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private EventTypeEnum type;
}

package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.persistence.pojo.enums.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "name", "description", "address", "price", "type", "happensOn", "createdDate", "lastModifiedDate"})
public class EventDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private BigDecimal price;
    private EventTypeEnum type;

    @ApiModelProperty(example = "2020-08-14 11:21")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime happensOn;

    @ApiModelProperty(example = "2020-08-14 11:21:53")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @ApiModelProperty(example = "2020-08-14 11:21:53")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
}

package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.example.persistence.pojo.enums.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventChangeRequest {

    @ApiModelProperty(name = "Name of the event")
    @NotEmpty
    @Size(min = 2, max = 255)
    private String name;

    @ApiModelProperty(name = "Description of the event", allowEmptyValue = true)
    @Size(max = 1000)
    private String description;

    @ApiModelProperty(name = "Address where the event is taking place")
    @NotEmpty
    @Size(max = 255)
    private String address;

    @ApiModelProperty(name = "Price of the event")
    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @ApiModelProperty(name = "Type of the event")
    @NotNull
    private EventTypeEnum type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(name = "Date and time when the event will take place", example = "2020-11-10 10:00")
    @NotNull
    private LocalDateTime happensOn;
}

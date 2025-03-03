package com.example.itmo.extended.model.dto.request;

import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.Color;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarsInfoReq {
    @NotEmpty
    @Schema(description = "Бренд")
    private String brand;

    @Schema(description = "Модель")
    private String model;

    @Schema(description = "Цвет")
    private Color color;

    @Schema(description = "Год выпуска")
    private Integer year;

    @Schema(description = "Цена автомобиля")
    private Long price;

    @Schema(description = "Новая/БУ")
    private Boolean isNew;

    @Schema(description = "Тип кузова")
    private CarType type;

    @NotNull
    @Schema(required = true, description = "Серийный номер")
    private Integer serialNumber;
}

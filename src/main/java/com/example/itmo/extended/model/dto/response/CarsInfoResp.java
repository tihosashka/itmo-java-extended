package com.example.itmo.extended.model.dto.response;

import com.example.itmo.extended.model.dto.request.CarsInfoReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarsInfoResp extends CarsInfoReq {
    @Schema(description = "Id автомобиля")
    private Long id;
    private UserInfoResp userInfo;
}

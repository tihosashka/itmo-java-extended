package com.example.itmo.extended.controllers;

import com.example.itmo.extended.model.dto.request.CarsInfoReq;
import com.example.itmo.extended.model.dto.response.CarsInfoResp;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.service.CarsService;
import com.example.itmo.extended.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили")
public class CarsController {
    private final CarsService carsService;
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить автомобиль по id")
    public CarsInfoResp getCar(@PathVariable Long id) {
        return carsService.getCar(id);
    }

    @PostMapping
    @Operation(summary = "Создать автомобиль")
    public CarsInfoResp addCar(@RequestBody @Valid CarsInfoReq req) {
        return carsService.addCar(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные автомобиля по id")
    public CarsInfoResp updateCar(@PathVariable Long id, @RequestBody CarsInfoReq req) {
        return carsService.updateCar(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить автомобиль по id")
    public void deleteCar(@PathVariable Long id) {
        carsService.deleteCar(id);
    }


    @PostMapping("/linkUserAndDriver/{carId}/{userId}")
    @Operation(summary = "Связать пользователя и автомобиль по id")
    public CarsInfoResp linkUserAndDriver(@PathVariable Long carId, @PathVariable Long userId) {
        return carsService.LinkCarAndDriver(carId, userId);
    }

    @GetMapping("/all/{userId}")
    @Operation(summary = "Получить список автомобилей по пользователю")
    public List<CarsInfoResp> getAllCars(@PathVariable Long userId) {
        return carsService.findCarByUserId(userId);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список автомобилей")
    public Page<CarsInfoResp> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer perPage,
                                         @RequestParam(defaultValue = "brand") String sort,
                                         @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                         @RequestParam(required = false) String filter) {
        return carsService.getAllCars(page, perPage, sort, order, filter);
    }

}

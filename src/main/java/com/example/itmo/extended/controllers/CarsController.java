package com.example.itmo.extended.controllers;

import com.example.itmo.extended.model.dto.request.CarsInfoReq;
import com.example.itmo.extended.model.dto.response.CarsInfoResp;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.service.CarsService;
import com.example.itmo.extended.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarsService carsService;
    private final UserService userService;

    @GetMapping("/{id}")
    public CarsInfoResp getCar(@PathVariable Long id) {
        return carsService.getCar(id);
    }

    @PostMapping
    public CarsInfoResp addCar(@RequestBody CarsInfoReq req) {
        return carsService.addCar(req);
    }

    @PutMapping("/{id}")
    public CarsInfoResp updateCar(@PathVariable Long id, @RequestBody CarsInfoReq req) {
        return carsService.updateCar(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carsService.deleteCar(id);
    }


    @PostMapping("/linkUserAndDriver/{carId}/{userId}")
    public CarsInfoResp linkUserAndDriver(@PathVariable Long carId, @PathVariable Long userId) {
        return carsService.LinkCarAndDriver(carId, userId);
    }

    @GetMapping("/all/{userId}")
    public List<CarsInfoResp> getAllCars(@PathVariable Long userId) {
        return carsService.findCarByUserId(userId);
    }

    @GetMapping("/all")
    public Page<CarsInfoResp> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer perPage,
                                         @RequestParam(defaultValue = "brand") String sort,
                                         @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                         @RequestParam(required = false) String filter) {
        return carsService.getAllCars(page, perPage, sort, order, filter);
    }

}

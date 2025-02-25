package com.example.itmo.extended.controllers;

import com.example.itmo.extended.model.dto.request.CarsInfoReq;
import com.example.itmo.extended.model.dto.response.CarsInfoResp;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.service.CarsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarsService carsService;

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

    @GetMapping("/all")
    public List<CarsInfoResp> getAllCars() {
        return carsService.getAllCars();
    }


}

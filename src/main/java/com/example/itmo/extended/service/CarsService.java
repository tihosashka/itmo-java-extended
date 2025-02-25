package com.example.itmo.extended.service;

import com.example.itmo.extended.model.db.entity.Cars;
import com.example.itmo.extended.model.db.entity.User;
import com.example.itmo.extended.model.db.repository.CarsRepository;
import com.example.itmo.extended.model.dto.request.CarsInfoReq;
import com.example.itmo.extended.model.dto.response.CarsInfoResp;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.model.enums.CarType;
import com.example.itmo.extended.model.enums.CarsStatus;
import com.example.itmo.extended.model.enums.Color;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarsService {
    private final ObjectMapper mapper;
    private final CarsRepository carsRepository;

    public CarsInfoResp addCar(CarsInfoReq req) {
        Cars cars = mapper.convertValue(req, Cars.class);
        cars.setStatus(CarsStatus.CREATED);

        Cars save = carsRepository.save(cars);

        return mapper.convertValue(save, CarsInfoResp.class);
    }

    public CarsInfoResp getCar(Long id) {
        Cars cars = getCarsFromDB(id);
        return mapper.convertValue(cars, CarsInfoResp.class);
    }
    private Cars getCarsFromDB(Long id) {
        Optional<Cars> optionalCars = carsRepository.findById(id);
        return optionalCars.orElse(new Cars());
    }

    public CarsInfoResp updateCar(Long id, CarsInfoReq req) {
        Cars carFromDB = getCarsFromDB(id);
        if (carFromDB.getId() == null) {
            return mapper.convertValue(carFromDB, CarsInfoResp.class);

        }
        Cars carReq = mapper.convertValue(req, Cars.class);
        carFromDB.setBrand(carReq.getBrand()==null ? carFromDB.getBrand(): carReq.getBrand());
        carFromDB.setModel(carReq.getModel()==null ? carFromDB.getModel(): carReq.getModel());
        carFromDB.setPrice(carReq.getPrice()==null ? carFromDB.getPrice(): carReq.getPrice());
        carFromDB.setIsNew(carReq.getIsNew()==null ? carFromDB.getIsNew(): carReq.getIsNew());
        carFromDB.setYear(carReq.getYear()==null ? carFromDB.getYear(): carReq.getYear());
        carFromDB.setType(carReq.getType()==null ? carFromDB.getType(): carReq.getType());
        carFromDB.setColor(carReq.getColor()==null ? carFromDB.getColor(): carReq.getColor());


        carFromDB = carsRepository.save(carFromDB);
        return mapper.convertValue(carFromDB, CarsInfoResp.class);
    }

    public void deleteCar(Long id) {
        Cars carsFromDB = getCarsFromDB(id);
        if (carsFromDB.getId() == null) {
            log.error("Car with id {} not found", id);
            return;
        }
        carsFromDB.setStatus(CarsStatus.DELETED);
        carsRepository.save(carsFromDB);
    }

    public List<CarsInfoResp> getAllCars() {
        return carsRepository.findAll().stream()
                .map(car -> mapper.convertValue(car, CarsInfoResp.class))
                .collect(Collectors.toList());
    }

}

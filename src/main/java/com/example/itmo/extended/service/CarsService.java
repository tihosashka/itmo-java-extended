package com.example.itmo.extended.service;

import com.example.itmo.extended.model.db.entity.Cars;
import com.example.itmo.extended.model.db.entity.User;
import com.example.itmo.extended.model.db.repository.CarsRepository;
import com.example.itmo.extended.model.dto.request.CarsInfoReq;
import com.example.itmo.extended.model.dto.response.CarsInfoResp;
import com.example.itmo.extended.model.dto.response.UserInfoResp;
import com.example.itmo.extended.model.enums.CarsStatus;
import com.example.itmo.extended.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarsService {
    private final ObjectMapper mapper;
    private final CarsRepository carsRepository;
    private final UserService userService;

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
        carFromDB.setBrand(carReq.getBrand() == null ? carFromDB.getBrand() : carReq.getBrand());
        carFromDB.setModel(carReq.getModel() == null ? carFromDB.getModel() : carReq.getModel());
        carFromDB.setPrice(carReq.getPrice() == null ? carFromDB.getPrice() : carReq.getPrice());
        carFromDB.setIsNew(carReq.getIsNew() == null ? carFromDB.getIsNew() : carReq.getIsNew());
        carFromDB.setYear(carReq.getYear() == null ? carFromDB.getYear() : carReq.getYear());
        carFromDB.setType(carReq.getType() == null ? carFromDB.getType() : carReq.getType());
        carFromDB.setColor(carReq.getColor() == null ? carFromDB.getColor() : carReq.getColor());


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

    public Page<CarsInfoResp> getAllCars(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {

        Pageable pageRequest = PaginationUtils.getPageRequest(page, perPage, sort, order);

        Page<Cars> cars;

        if (StringUtils.hasText(filter)) {
            cars = carsRepository.findAllFiltered(pageRequest, filter);
        } else {
            cars = carsRepository.findAll(pageRequest);
        }
        List<CarsInfoResp> content = cars.getContent().stream()
                .map(x -> mapper.convertValue(x, CarsInfoResp.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageRequest, cars.getTotalElements());

    }

    public CarsInfoResp LinkCarAndDriver(Long carId, Long userId) {
        Cars carFromDB = getCarsFromDB(carId);
        User userFromDB = userService.getUserFromDB(userId);

        if (carFromDB.getId() == null || userFromDB.getId() == null) {
            return CarsInfoResp.builder().build();
        }
        List<Cars> cars = userFromDB.getCars();

        Cars existingCar = cars.stream().filter(car -> car.getId().equals(carId)).findFirst().orElse(null);

        if (existingCar != null) {
            return mapper.convertValue(existingCar, CarsInfoResp.class);
        }
        cars.add(carFromDB);
        User user = userService.updateCarList(userFromDB);

        carFromDB.setUser(user);
        carsRepository.save(carFromDB);
        CarsInfoResp carsInfoResp = mapper.convertValue(carFromDB, CarsInfoResp.class);
        UserInfoResp userInfoResp = mapper.convertValue(user, UserInfoResp.class);

        carsInfoResp.setUserInfo(userInfoResp);
        return carsInfoResp;

    }

    public  List<CarsInfoResp> findCarByUserId(Long userId) {
        return carsRepository.findByUserId(userId).stream()
                .map(cars -> mapper.convertValue(cars, CarsInfoResp.class))
                .collect(Collectors.toList());
    }
}


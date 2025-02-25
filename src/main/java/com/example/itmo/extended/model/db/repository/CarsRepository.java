package com.example.itmo.extended.model.db.repository;

import com.example.itmo.extended.model.db.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {
}

package com.example.itmo.extended.model.db.repository;

import com.example.itmo.extended.model.db.entity.Cars;
import com.example.itmo.extended.model.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {

    @Query(nativeQuery = true, value = "select * from cars where user_id = :userId")
    List<Cars> findByUserId(@Param("userId")Long userId);

    @Query("select c from Cars c where c.brand like %:filter% ")
    Page<Cars> findAllFiltered(Pageable pageRequest, @Param("filter") String filter);

    Optional<Cars> findBySerialNumber(Integer serialNumber);
}

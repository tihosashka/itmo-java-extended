package com.example.itmo.extended.service;

import com.example.itmo.extended.model.db.entity.Cars;
import com.example.itmo.extended.model.db.entity.User;
import com.example.itmo.extended.model.dto.request.UserInfoReq;
import com.example.itmo.extended.model.dto.response.UserInfoResp;

import java.util.List;

public interface UserService {
    UserInfoResp addUser(UserInfoReq req);

    UserInfoResp getUser(Long id);

    User getUserFromDB(Long id);

    UserInfoResp updateUser(Long id, UserInfoReq req);

    void deleteUser(Long id);

    List<UserInfoResp> getAllUsers();

    User updateCarList(User userFromDB);
}

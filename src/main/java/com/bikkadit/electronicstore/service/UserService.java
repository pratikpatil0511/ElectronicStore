package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.UserDto;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(String id,UserDto userDto);

    //delete
    void deleteUser(String id);

    //get all users
    List<UserDto> getAllUsers();

    //get user by id
    UserDto getUserById(String id);

    //get user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUsers(String keywords);
}

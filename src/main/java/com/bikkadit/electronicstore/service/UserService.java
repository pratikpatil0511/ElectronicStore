package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.helper.PageableResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(String id,UserDto userDto);

    //delete
    void deleteUser(String id) throws IOException;

    //get all users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get user by id
    UserDto getUserById(String id);

    //get user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUsers(String keywords);
}

package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;

import java.util.List;

public interface UserService {
    //create
    UserDto CreateUser(UserDto userDto);
    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);
    //get all users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
    //get single by id
    UserDto getUserById(String userId);
    //get user by email
   UserDto getUserByEmail(String email);

    //search dto

    List<UserDto> searchUser(String Keyword);
    //other user specific features

}

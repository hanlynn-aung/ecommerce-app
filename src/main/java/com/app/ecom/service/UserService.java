package com.app.ecom.service;


import com.app.ecom.controller.request.UserRequest;
import com.app.ecom.controller.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<UserResponse> getUserList();

    public void createUser(UserRequest user);

    Optional<UserResponse> getUser(Long id);

    boolean updateUser(Long id,UserRequest user);
}

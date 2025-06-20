package com.app.ecom.service;


import com.app.ecom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getUserList();

    public void createUser(User user);

    Optional<User> getUser(Long id);

    boolean updateUser(Long id,User user);
}

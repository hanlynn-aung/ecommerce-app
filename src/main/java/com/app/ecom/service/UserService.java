package com.app.ecom.service;


import com.app.ecom.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getUserList();

    public List<User> createUser(User user);

    User getUser(int id);
}

package com.app.ecom.serviceImpl;

import com.app.ecom.entity.User;
import com.app.ecom.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> userList  = new ArrayList<>();

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public User getUser(int id) {
       for(User user:userList){
           if(user.getId()==id){
               return user;
           }
       }
       return null;
    }

    @Override
    public List<User> createUser(User user) {
        userList.add(user);
        return userList;
    }
}

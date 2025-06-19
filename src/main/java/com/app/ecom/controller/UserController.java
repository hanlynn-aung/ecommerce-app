package com.app.ecom.controller;

import com.app.ecom.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(){
        return null;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user){
        return user;
    }
}

package com.app.ecom.controller;

import com.app.ecom.common.constant.AppConstant;
import com.app.ecom.common.response.HttpResponse;
import com.app.ecom.controller.request.UserRequest;
import com.app.ecom.controller.response.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {

        Map<String, Object> data = new HashMap<>();

        List<UserResponse> userResponses = this.userService.getUserList();

        data.put("users", userResponses);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE,  data);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        Map<String, Object> data = new HashMap<>();

        Optional<UserResponse> userResponse = this.userService.getUser(id);

        userResponse.ifPresent(response ->
                data.put("user", response));

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE,  data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody UserRequest user) {

        Map<String, Object> data = new HashMap<>();

        UserResponse userResponse =  this.userService.createUser(user);

        data.put("user", userResponse);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE,  data);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest user) {

        Map<String, Object> data = new HashMap<>();
        boolean updated = this.userService.updateUser(id, user);

        if (updated) {
            data.put("user", "User updated successfully.");
        }
        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE,  data);
    }
}

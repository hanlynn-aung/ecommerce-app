package com.app.ecom.serviceImpl;

import com.app.ecom.entity.User;
import com.app.ecom.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> userList = new ArrayList<>();

    private static Long id = 1L;

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public Optional<User> getUser(Long id) {
       return userList.stream()
               .filter(u -> u.getId().equals(id))
               .findFirst();
    }

    @Override
    public void createUser(User user) {
        user.setId(id++);
        userList.add(user);
    }

    @Override
    public boolean updateUser(Long id, User updateUser) {

        return userList.stream()
                .filter(
                        user->user.getId().equals(id)
                )
                .findFirst()
                .map(existingUser->{
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    return true;
                        })
                .orElse(false);
    }
}

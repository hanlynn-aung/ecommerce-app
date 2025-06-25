package com.app.ecom.serviceImpl;

import com.app.ecom.controller.request.UserRequest;
import com.app.ecom.controller.response.AddressResponse;
import com.app.ecom.controller.response.UserResponse;
import com.app.ecom.entity.Address;
import com.app.ecom.entity.User;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.UserService;
import com.app.ecom.util.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

    @Override
    public List<UserResponse> getUserList() {
        List<User> users =  this.userRepository.findAll();

        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> getUser(Long id) {
       return this.userRepository.findById(id)
               .map(this::mapToUserResponse);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        User user = Builder.of(User::new)
                        .build();

        user = createOrUpateUser(user, userRequest);

        this.userRepository.save(user);

        return mapToUserResponse(user);
    }

    @Override
    public boolean updateUser(Long id, UserRequest updateUser) {

        return this.userRepository.findById(id)
                .map(existingUser->{
                    createOrUpateUser(existingUser, updateUser);
                    this.userRepository.save(existingUser);
                    return true;
                        })
                .orElse(false);
    }


    private static User createOrUpateUser(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());

        if (userRequest.getAddress() != null) {
            Address address = user.getAddress() != null ? user.getAddress() : new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());

            user.setAddress(address);
        }

        return user;
    }


    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = Builder.of(UserResponse::new)
                .add(UserResponse::setFirstName, user.getFirstName())
                .add(UserResponse::setLastName, user.getLastName())
                .add(UserResponse::setEmail, user.getEmail())
                .add(UserResponse::setPhoneNumber, user.getPhoneNumber())
                .add(UserResponse::setRole, user.getRole())
                .build();

        AddressResponse addressResponse = null;
        if (user.getAddress() != null) {
          addressResponse  = Builder.of(AddressResponse::new)
                    .add(AddressResponse::setStreet, user.getAddress().getStreet())
                    .add(AddressResponse::setCity, user.getAddress().getCity())
                    .add(AddressResponse::setState, user.getAddress().getState())
                    .add(AddressResponse::setCountry, user.getAddress().getCountry())
                    .add(AddressResponse::setZipCode, user.getAddress().getZipCode())
                    .build();
        }

        userResponse.setAddress(addressResponse);

        return userResponse;
    }
}

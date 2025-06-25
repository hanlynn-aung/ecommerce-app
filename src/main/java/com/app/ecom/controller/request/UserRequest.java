package com.app.ecom.controller.request;

import com.app.ecom.common.constant.UserRole;
import com.app.ecom.controller.response.AddressResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role")
    private UserRole role;

    @JsonProperty("address")
    private AddressResponse address;
}

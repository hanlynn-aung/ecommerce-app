package com.app.ecom.controller.request;

import com.app.ecom.common.constact.UserRole;
import com.app.ecom.controller.response.AddressResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private AddressResponse address;
}

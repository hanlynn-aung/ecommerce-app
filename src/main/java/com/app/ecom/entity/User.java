package com.app.ecom.entity;

import com.app.ecom.common.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phoneNumber;

    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}

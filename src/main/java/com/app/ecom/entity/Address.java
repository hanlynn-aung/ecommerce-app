package com.app.ecom.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address extends AuditEntity {

    private String street;

    private String city;

    private String state;

    private String country;

    private String zipCode;
}

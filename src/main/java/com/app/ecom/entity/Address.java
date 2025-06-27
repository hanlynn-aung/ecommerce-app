package com.app.ecom.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address extends AuditEntity {

    private String street;

    private String city;

    private String state;

    private String country;

    private String zipCode;
}

package com.revature.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    
    @Id
    @Column(name = "address_id", length = 8)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(name = "street_address", length = 100, nullable = false)
    private String streetAddress;

    @Column(name = "street_address_line_2", length = 30)
    private String streetAddressLine2;

    @Column(length = 20, nullable = false)
    private String city;

    @Column(length = 2, nullable = false)
    private String state;

    @Column(name = "zip_code", length = 5, nullable = false)
    private Integer zipCode;
    
}

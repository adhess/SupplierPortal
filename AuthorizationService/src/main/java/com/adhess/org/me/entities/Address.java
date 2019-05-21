package com.adhess.org.me.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class Address implements Serializable {
    private String city;
    private String state;
    private Integer zipCode;

    public Address(String city, String state, Integer zipCode) {
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}

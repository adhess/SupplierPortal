package com.adhess.org.me.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUser {
    private String username;
    private String password;
    private String name;
    private String telephone;
    private String fax;
    private String email;
    private String activitySector;
    private int employeeNumber;
    private String company_number;
    private String tva;
    private String city;
    private String state;
    private int zipCode;
    private String []authorities;
}

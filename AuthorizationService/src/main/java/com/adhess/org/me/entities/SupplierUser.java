package com.adhess.org.me.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data @NoArgsConstructor
public class SupplierUser extends AppUser implements Serializable {

    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private String activitySector;
    private int employeeNumber;
    private String company_number;
    private String tva;

    public SupplierUser(String username, String password, String name, String telephone, String fax,
                        String email, String activitySector, int employeeNumber, String company_number, String tva) {
        super(username, password, name, telephone, fax, email);
        this.activitySector = activitySector;
        this.employeeNumber = employeeNumber;
        this.company_number = company_number;
        this.tva = tva;
    }
}

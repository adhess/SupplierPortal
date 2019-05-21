package com.adhess.org.me.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class ContactUser extends AppUser implements Serializable  {

    @ManyToOne
    private SupplierUser supplier;
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    public ContactUser(String username, String password, String name, String telephone, String fax, String email) {
        super(username, password, name, telephone, fax, email);
    }
}


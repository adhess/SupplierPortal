package com.adhess.org.me.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String telephone;
    private String fax;
    private String email;

    @Embedded
    private Address address;

    private AppRole role = AppRole.NONE;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority>  authorities;

    public AppUser(String username, String password, String name, String telephone, String fax, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.fax = fax;
        this.email = email;
    }
}


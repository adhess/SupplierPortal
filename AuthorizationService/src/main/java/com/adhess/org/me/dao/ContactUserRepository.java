package com.adhess.org.me.dao;

import com.adhess.org.me.entities.ContactUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactUserRepository extends JpaRepository<ContactUser,Long> {
    public ContactUser findByUsername(String username);
    public List<ContactUser> findBySupplier_Username(String username);
}

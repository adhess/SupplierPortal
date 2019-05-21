package com.adhess.org.me.dao;

import com.adhess.org.me.entities.SupplierUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierUserRepository extends JpaRepository<SupplierUser,Long> {
    SupplierUser findByUsername(String username);
}

package com.adhess.org.me.dao;

import com.adhess.org.me.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    public Authority findByAuthority(String authority);
}

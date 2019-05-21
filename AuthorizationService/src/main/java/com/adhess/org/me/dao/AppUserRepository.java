package com.adhess.org.me.dao;

import com.adhess.org.me.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "appUser", path = "appUser")
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    public AppUser findByUsername(String username);
    @Query("select a.username from AppUser a")
    public List<String> findAllUsername();
}

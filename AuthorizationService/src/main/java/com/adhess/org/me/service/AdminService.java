package com.adhess.org.me.service;

import com.adhess.org.me.dao.AppUserRepository;
import com.adhess.org.me.dao.ContactUserRepository;
import com.adhess.org.me.dao.SupplierUserRepository;
import com.adhess.org.me.entities.*;
import com.adhess.org.me.model.AffectSupplierModel;
import com.adhess.org.me.model.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
public class AdminService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SupplierUserRepository supplierUserRepository;

    @Autowired
    ContactUserRepository contactUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/admin/createAdminAccount", method = RequestMethod.POST)
    public AppUser createAdminAccount(@RequestBody RegisterUser reg) {
        Address address = new Address(reg.getCity(), reg.getState(), reg.getZipCode());

        return createAdminAccount(reg.getUsername(),reg.getPassword(), reg.getName(),
                reg.getTelephone(), reg.getFax(), reg.getEmail(), address
        );
    }

    public AppUser createAdminAccount(String username, String password, String name, String telephone,
                                      String fax, String email, Address address) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("Admin Already Exist...");
        user = new AppUser(username, bCryptPasswordEncoder.encode(password), name, telephone, fax, email);
        user.setRole(AppRole.ADMIN);
        user.setAddress(address);
        return appUserRepository.save(user);
    }

    public boolean noAdmin() {
        List<AppUser> all = appUserRepository.findAll();
        return all == null || all.isEmpty();
    }

    @RequestMapping(value = "admin/find_all_user", method = RequestMethod.GET)
    public List findAllUser() {
        return this.appUserRepository.findAll();
    }
}

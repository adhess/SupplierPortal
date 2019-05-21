package com.adhess.org.me.service;

import com.adhess.org.me.dao.AppUserRepository;
import com.adhess.org.me.dao.ContactUserRepository;
import com.adhess.org.me.dao.SupplierUserRepository;
import com.adhess.org.me.entities.Address;
import com.adhess.org.me.entities.AppUser;
import com.adhess.org.me.entities.SupplierUser;
import com.adhess.org.me.model.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
public class UserService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SupplierUserRepository supplierUserRepository;

    @Autowired
    ContactUserRepository contactUserRepository;

    @RequestMapping(value = "auth/getUserInfo", method = RequestMethod.POST)
    public AppUser getUserInfo() {
        String username = AuthenticatedHelper.findUsername();
        if (AuthenticatedHelper.isAdmin()) {
            return appUserRepository.findByUsername(username);
        } else if (AuthenticatedHelper.isSupplier()) {
            return supplierUserRepository.findByUsername(username);
        } else if (AuthenticatedHelper.isContact()) {
            return contactUserRepository.findByUsername(username);
        }
        throw new RuntimeException("User not allowed exception...");
    }




    @RequestMapping(value = "auth/updateCurrentUser", method = RequestMethod.PUT)
    public AppUser updateCurrentUser(@RequestBody RegisterUser reg) {
        System.out.println(reg.toString());
        String username = AuthenticatedHelper.findUsername();
        AppUser user = appUserRepository.findByUsername(username);
        Address address = user.getAddress();
        if (address == null) address = new Address();
        address.setCity(reg.getCity());
        address.setState(reg.getState());
        address.setZipCode(reg.getZipCode());
        user.setName(reg.getName());
        user.setEmail(reg.getEmail());
        user.setFax(reg.getFax());
        user.setTelephone(reg.getTelephone());
        AppUser save = appUserRepository.save(user);
        if (AuthenticatedHelper.isAdmin() || AuthenticatedHelper.isContact()) {
            return save;
        } else if (AuthenticatedHelper.isSupplier()) {
            SupplierUser userSup = supplierUserRepository.findByUsername(username);
            userSup.setTva(reg.getTva());
            userSup.setActivitySector(reg.getActivitySector());
            userSup.setCompany_number(reg.getCompany_number());
            userSup.setEmployeeNumber(reg.getEmployeeNumber());
            return supplierUserRepository.save(userSup);
        }
        throw new RuntimeException("User not allowed exception...");
    }

    @RequestMapping(value = "getAllUsername", method = RequestMethod.GET)
    public List<String> getAllUsername() {
        return this.appUserRepository.findAllUsername();
    }
}
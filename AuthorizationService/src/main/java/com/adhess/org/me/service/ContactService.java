package com.adhess.org.me.service;

import com.adhess.org.me.dao.AppUserRepository;
import com.adhess.org.me.dao.AuthorityRepository;
import com.adhess.org.me.dao.ContactUserRepository;
import com.adhess.org.me.dao.SupplierUserRepository;
import com.adhess.org.me.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
public class ContactService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    SupplierUserRepository supplierUserRepository;

    @Autowired
    ContactUserRepository contactUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SupplierService supplierService;

    public boolean noContact() {
        List<ContactUser> all = contactUserRepository.findAll();
        return all == null || all.isEmpty();
    }

    public ContactUser createContactAccount(String username, String password, String name, String telephone,
                                            String fax, String email, Address address) {
        ContactUser user = contactUserRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("Contact Already Exist...");
        user = new ContactUser(username, bCryptPasswordEncoder.encode(password), name, telephone, fax, email);
        user.setRole(AppRole.CONTACT);
        user.setAddress(address);
        return appUserRepository.save(user);
    }

    public ContactUser createContactAccount(String username, String password, String name, String telephone,
                                            String fax, String email, Address address, String supplier_username, String[] authorities) {

        ContactUser contactAccount = createContactAccount(username, password, name, telephone,
                fax, email, address);
        List<Authority> authorityList = new ArrayList<>();
        if (authorities != null)
            for (String authority : authorities) {
                Authority byAuthority = authorityRepository.findByAuthority(authority);
                if (byAuthority == null)
                    throw new RuntimeException("No Authority(" + authority + ") found exception...");
                authorityList.add(byAuthority);
            }
        contactAccount.setAuthorities(authorityList);
        SupplierUser supplier = this.supplierUserRepository.findByUsername(supplier_username);
        if (supplier == null) throw new RuntimeException("Supplier does not exist exception...");
        contactAccount.setSupplier(supplier);
        return contactUserRepository.save(contactAccount);
    }

    @RequestMapping(value = "auth/findAllContacts", method = RequestMethod.GET)
    public List findAllContact() {
        String supplierUsername = "";
        if (AuthenticatedHelper.isSupplier()) {
            supplierUsername = AuthenticatedHelper.findUsername();
        } else if (AuthenticatedHelper.isContact() && AuthenticatedHelper.isReadContacts()) {
            String contactUsername = AuthenticatedHelper.findUsername();
            supplierUsername = supplierService.getSupplierUsername(contactUsername);
        } else throw new RuntimeException("User not allowed to read resources exception...");
        return this.contactUserRepository.findBySupplier_Username(supplierUsername);
    }
}

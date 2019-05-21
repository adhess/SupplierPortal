package com.adhess.org.me.service;

import com.adhess.org.me.dao.ContactUserRepository;
import com.adhess.org.me.dao.SupplierUserRepository;
import com.adhess.org.me.entities.*;
import com.adhess.org.me.model.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Service
@RestController
public class SupplierService {

    @Autowired
    private SupplierUserRepository supplierUserRepository;

    @Autowired
    private ContactUserRepository contactUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String createSupplierAccount(@RequestBody RegisterUser reg) {
        Address address = new Address(reg.getCity(), reg.getState(), reg.getZipCode());

        return createSupplierAccount(reg.getUsername(),
                reg.getPassword(), reg.getName(),
                reg.getTelephone(), reg.getFax(), reg.getEmail(),
                reg.getActivitySector(), reg.getEmployeeNumber(),
                reg.getCompany_number(), reg.getTva(), address
        );
    }

    public String createSupplierAccount(String username, String password, String name, String telephone, String fax,
                                        String email, String activitySector, int employeeNumber, String company_number,
                                        String tva, Address address) {
        SupplierUser user = supplierUserRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("Supplier Already Exist...");
        SupplierUser supplier = new SupplierUser(username, bCryptPasswordEncoder.encode(password), name, telephone, fax,
                email, activitySector, employeeNumber, company_number, tva
        );
        supplier.setAddress(address);
        supplier.setRole(AppRole.SUPPLIER);

        if (supplierUserRepository.save(supplier) != null)
            return "ok";
        return "ko";
    }

    public boolean noSupplier() {
        List<SupplierUser> all = supplierUserRepository.findAll();
        return all == null || all.isEmpty();
    }



    @RequestMapping(value = "supplier/addContact", method = RequestMethod.POST)
    public ContactUser addContact(@RequestBody RegisterUser reg) {
        String username = AuthenticatedHelper.findUsername();
        Address address = new Address(reg.getCity(), reg.getState(), reg.getZipCode());

        return this.contactService.createContactAccount(reg.getUsername(),
                reg.getPassword(), reg.getName(),
                reg.getTelephone(), reg.getFax(), reg.getEmail(), address, username, reg.getAuthorities()

        );
    }
    @RequestMapping(value = "getSupplierUsername/{contact}", method = RequestMethod.GET)
    public String getSupplierUsername(@PathVariable String contact) {
        ContactUser contactUser = this.contactUserRepository.findByUsername(contact);
        if (contactUser == null) throw new RuntimeException("Contact: " + contact + " does not exist exception...");
        SupplierUser supplier = contactUser.getSupplier();
        if (supplier == null)
            throw new RuntimeException("Contact: " + contact + " does not have a supplier exception...");
        return supplier.getUsername();
    }



}

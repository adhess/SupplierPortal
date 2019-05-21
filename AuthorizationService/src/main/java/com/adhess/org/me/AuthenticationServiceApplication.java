package com.adhess.org.me;

import com.adhess.org.me.dao.AuthorityRepository;
import com.adhess.org.me.entities.Address;
import com.adhess.org.me.entities.Authority;
import com.adhess.org.me.service.AdminService;
import com.adhess.org.me.service.ContactService;
import com.adhess.org.me.service.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationServiceApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }


    @Bean
    CommandLineRunner start(AdminService adminService, SupplierService supplierService, ContactService contactService,
                            AuthorityRepository authorityRepository) {
        return args -> {
            List<Authority> authorities = authorityRepository.findAll();
            if (authorities == null || authorities.isEmpty()) {
                authorityRepository.save(new Authority("readOrders"));
                authorityRepository.save(new Authority("readAndWriteOrders"));
                authorityRepository.save(new Authority("readInvoices"));
                authorityRepository.save(new Authority("readAndWriteInvoices"));
                authorityRepository.save(new Authority("readContacts"));
                authorityRepository.save(new Authority("readAndWriteContacts"));
            }
            if (adminService.noAdmin()) {
                adminService.createAdminAccount("admin", "admin", "admin",
                        "+(216) 66 666 666", "+(216) 66 666 666", "admin@gmail.com",
                        new Address("ad","ad",19));
            }


            if (contactService.noContact()) {
                contactService.createContactAccount("contact5", "contact", "contact1",
                        "+(216) 55 555 555", "+(216) 55 555 555", "contact@gmail.com",
                        new Address("pariSSa", "pariSSa", 5555555));
            }

            if (supplierService.noSupplier()) {
                supplierService.createSupplierAccount("supplier", "supplier", "supplier",
                        "+(216) 99 999 999", "+(216) 99 999 999", "supplier@gmail.com", "sector 1", 999, "548798.3265.21",
                        "87986.554.2154", new Address("paris", "paris", 549865));
                String []authorityList = new String[6];
                int i = 0;
                for (Authority authority : authorityRepository.findAll()) {
                    authorityList[i++] = authority.getAuthority();
                }
                contactService.createContactAccount("contact", "contact", "contact",
                        "+(216) 99 999 999", "+(216) 99 999 999", "contact@gmail.com",
                        new Address("paris", "paris", 549865), "supplier", authorityList);

                authorityList = new String[]{"readOrders"};
                contactService.createContactAccount("contact1", "contact", "contact",
                        "+(216) 99 999 999", "+(216) 99 999 999", "contact@gmail.com",
                        new Address("paris", "paris", 549865), "supplier", authorityList);
                authorityList = new String[]{"readInvoices"};
                contactService.createContactAccount("contact2", "contact", "contact",
                        "+(216) 99 999 999", "+(216) 99 999 999", "contact@gmail.com",
                        new Address("paris", "paris", 549865), "supplier", authorityList);
                authorityList = new String[]{"readContacts"};
                contactService.createContactAccount("contact3", "contact", "contact",
                        "+(216) 99 999 999", "+(216) 99 999 999", "contact@gmail.com",
                        new Address("paris", "paris", 549865), "supplier", authorityList);
                authorityList = new String[]{"readOrders","readInvoices"};
                contactService.createContactAccount("contact4", "contact", "contact",
                        "+(216) 99 999 999", "+(216) 99 999 999", "contact@gmail.com",
                        new Address("paris", "paris", 549865), "supplier", authorityList);

            }
        };
    }

    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

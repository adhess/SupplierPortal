package com.adhess.org.p2p.service.p2p_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Objects;

public class AuthenticatedHelper {
    public static String findUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    public static Collection<? extends GrantedAuthority> findAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getAuthorities();
        }
        return null;
    }

    public static boolean isAdmin() {
        for (GrantedAuthority authority : Objects.requireNonNull(findAuthorities())) {
            if (authority.getAuthority().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }
    public static boolean isSupplier() {
        for (GrantedAuthority authority : Objects.requireNonNull(findAuthorities())) {
            if (authority.getAuthority().equals("SUPPLIER")) {
                return true;
            }
        }
        return false;
    }
    public static boolean isContact() {
        for (GrantedAuthority authority : Objects.requireNonNull(findAuthorities())) {
            if (authority.getAuthority().equals("CONTACT")) {
                return true;
            }
        }
        return false;
    }

    public static String getSupplierUsername() {
        String username = AuthenticatedHelper.findUsername();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:7999/getSupplierUsername/" + username;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);
        return responseEntity.getBody();
    }

    public static boolean isOrderRead() {
        for (GrantedAuthority authority : findAuthorities()) {
            if (authority.getAuthority().equals("readAndWriteOrders")
                    ||authority.getAuthority().equals("readOrders")) return true;
        }
        return false;
    }

    public static boolean isInvoiceRead() {
        for (GrantedAuthority authority : findAuthorities()) {
            if (authority.getAuthority().equals("readAndWriteInvoices")
                    ||authority.getAuthority().equals("readInvoices")) return true;
        }
        return false;
    }
}

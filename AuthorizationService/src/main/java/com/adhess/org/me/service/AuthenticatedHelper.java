package com.adhess.org.me.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static boolean isReadContacts() {
        for (GrantedAuthority authority : Objects.requireNonNull(findAuthorities())) {
            if (authority.getAuthority().equals("readContacts")|| authority.getAuthority().equals("readAndWriteContacts")) {
                return true;
            }
        }
        return false;
    }
}

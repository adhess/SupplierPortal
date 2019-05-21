package com.adhess.org.me.security;

import com.adhess.org.me.dao.AppUserRepository;
import com.adhess.org.me.entities.AppUser;
import com.adhess.org.me.entities.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AppUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.applicationUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = applicationUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().name()));
        for (Authority authority : appUser.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        UserDetailsImpl user = new UserDetailsImpl(appUser.getUsername(), appUser.getPassword(), authorities);
        return user;
    }


}

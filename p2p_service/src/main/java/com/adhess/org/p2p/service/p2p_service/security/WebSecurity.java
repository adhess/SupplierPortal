package com.adhess.org.p2p.service.p2p_service.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/supplier/**").hasAuthority("SUPPLIER")
                .antMatchers(HttpMethod.GET, "/supplier/**").hasAuthority("SUPPLIER")
                .antMatchers(HttpMethod.POST, "/auth/**").authenticated()
                .antMatchers(HttpMethod.GET, "/auth/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }
}

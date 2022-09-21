package com.phoenix.security;

import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Authority;
import com.phoenix.data.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //query for user details from db
        AppUser user = appUserRepository.findByEmail(username).orElse(null);

        //check that user exists
        if(user == null) throw new UsernameNotFoundException("User with email " + username + " not found");

        //return userDetails
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Authority> authorities){
        return authorities.stream().map(
                authority -> {
                    return new SimpleGrantedAuthority(authority.name());
                }).collect(Collectors.toList());
    }
}

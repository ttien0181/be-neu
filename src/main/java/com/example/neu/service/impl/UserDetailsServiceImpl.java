package com.example.neu.service.impl;

import com.example.neu.entity.User;
import com.example.neu.repository.UserRepository;
import com.example.neu.entity.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for loading user-specific data.
 */
@Service
//@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Loading user by email: " + email);
        return userRepository.findByEmail(email)
                .map(u -> {
                    System.out.println("✅ Found user: " + u.getEmail() + " role: " + u.getRole());
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));
                    return new org.springframework.security.core.userdetails.User(
                            u.getEmail(),
                            u.getPassword(),
                            authorities
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    }
}
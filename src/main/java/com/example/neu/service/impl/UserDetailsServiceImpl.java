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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        String roleUpper = u.getRole().name(); // nếu là enum Role {USER, ADMIN}
        // nếu trường role là String: String roleUpper = u.getRole().toUpperCase();

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleUpper));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                authorities
        );
    }
}
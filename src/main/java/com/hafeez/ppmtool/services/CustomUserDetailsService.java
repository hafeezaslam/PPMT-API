package com.hafeez.ppmtool.services;

import com.hafeez.ppmtool.domain.User;
import com.hafeez.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

package com.hafeez.ppmtool.services;

import com.hafeez.ppmtool.domain.User;
import com.hafeez.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.hafeez.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmPassword(user.getPassword());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exist");
        }
    }
}

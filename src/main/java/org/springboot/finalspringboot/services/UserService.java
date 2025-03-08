package org.springboot.finalspringboot.services;

import org.springboot.finalspringboot.model.entities.Users;
import org.springboot.finalspringboot.model.repotories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
        );
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users registerUser(Users user) {
        // Mã hóa password trước khi lưu
        user.setPassword(user.getPassword());
        user.setRole("Admin");
        user.setUsername(user.getEmail());
        // Lưu người dùng vào DB
        return userRepository.save(user);
    }
}
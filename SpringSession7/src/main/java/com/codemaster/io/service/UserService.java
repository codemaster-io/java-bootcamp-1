package com.codemaster.io.service;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.User;
import com.codemaster.io.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long addUser(User user) {
        long id = System.currentTimeMillis();
        String passHash = passwordEncoder.encode(user.getPassword());
        System.out.println("passHash = " + passHash);

        user = user.toBuilder()
                .id(id)
                .password(passHash)
                .build();
        boolean success = userRepository.addUser(user);
        System.out.println("success = " + success);
        if(success) return id;
        return -1;
    }

    public boolean updateUser(User user) {
        userRepository.deleteUser(user.getEmail());
        String passHash = passwordEncoder.encode(user.getPassword());
        user = user.toBuilder()
                .password(passHash)
                .build();
        userRepository.addUser(user);
        return true;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUser(email);
    }

    public User getUserById(long id) {
        for(User user : userRepository.getUsers()) {
            if(user.getId() == id) return user;
        }
        return null;
    }

    public boolean deleteUserById(long id) {
        User user = getUserById(id);
        if(user != null) return userRepository.deleteUser(user.getEmail());
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(email == null) throw new UsernameNotFoundException("Email is null");

        User user = userRepository.getUser(email);
        if(user == null) throw new UsernameNotFoundException("User not found");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));

        for(Permission permission : user.getPermissions()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.toString()));
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
        return userDetails;
    }
}

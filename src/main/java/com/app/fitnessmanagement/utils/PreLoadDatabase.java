package com.app.fitnessmanagement.utils;

import com.app.fitnessmanagement.model.User;
import com.app.fitnessmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PreLoadDatabase {

    private final UserRepository userRepository;

    public PreLoadDatabase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //A super admin already added. You can create other users from here as discussed.
    public void insertAdmin(PasswordEncoder passwordEncoder) {
        Optional<User> user = userRepository.findUserByEmail("admin@gmail.com");
        if (user.isEmpty()) {
            User admin = new User();
            admin.setFullName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setAddress("37 Middleway View, B18 7DD, UK");
            admin.setAge(30);
            admin.setHeight(180);
            admin.setWeight(70);
            admin.setGender("Male");
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
    }
}

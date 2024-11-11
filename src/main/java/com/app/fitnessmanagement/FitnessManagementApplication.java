package com.app.fitnessmanagement;

import com.app.fitnessmanagement.utils.PreLoadDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FitnessManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FitnessManagementApplication.class, args);
    }

    @Autowired
    PreLoadDatabase preLoadDatabase;

    @Override
    public void run(String... args) throws Exception {
        preLoadDatabase.insertAdmin(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

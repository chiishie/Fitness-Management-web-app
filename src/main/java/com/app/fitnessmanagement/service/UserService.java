package com.app.fitnessmanagement.service;

import com.app.fitnessmanagement.model.User;
import com.app.fitnessmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateProfile(User updatedUserDetails) {
        Optional<User> existingUser = userRepository.findById(updatedUserDetails.getId());
        if (existingUser.isPresent()) {
            existingUser.get().setFullName(updatedUserDetails.getFullName());
            existingUser.get().setEmail(updatedUserDetails.getEmail());
            existingUser.get().setAddress(updatedUserDetails.getAddress());
            existingUser.get().setAge(updatedUserDetails.getAge());
            existingUser.get().setHeight(updatedUserDetails.getHeight());
            existingUser.get().setWeight(updatedUserDetails.getWeight());
            existingUser.get().setGender(updatedUserDetails.getGender());
            if (updatedUserDetails.getPassword() != null) {
                existingUser.get().setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
            }
            return userRepository.save(existingUser.get());
        } else {
            return null;
        }
    }


    public User updateUser(User updatedUserDetails) {
        Optional<User> existingUser = userRepository.findById(updatedUserDetails.getId());
        if (existingUser.isPresent()) {
            existingUser.get().setFullName(updatedUserDetails.getFullName());
            existingUser.get().setEmail(updatedUserDetails.getEmail());
            existingUser.get().setAddress(updatedUserDetails.getAddress());
            existingUser.get().setAge(updatedUserDetails.getAge());
            existingUser.get().setHeight(updatedUserDetails.getHeight());
            existingUser.get().setWeight(updatedUserDetails.getWeight());
            existingUser.get().setGender(updatedUserDetails.getGender());
            if (updatedUserDetails.getPassword() != null) {
                existingUser.get().setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
            }
            existingUser.get().setRole(updatedUserDetails.getRole());
            return userRepository.save(existingUser.get());
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userRepository.findUserByEmail(email);
        user.ifPresent(users::remove);
        return users;
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            log.debug("User not found with email {}", email);
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.get().getRole()));
        return new org.springframework.security.core.userdetails.User(
                email,
                user.get().getPassword(),
                authorities
        );
    }

}

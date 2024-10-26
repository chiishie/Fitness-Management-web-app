package com.app.fitnessmanagement.controller;

import com.app.fitnessmanagement.model.User;
import com.app.fitnessmanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/")
    public String home(HttpSession session) {
        Authentication authentication = getAuthenticationPrincipal();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userService.findUserByEmail(authentication.getName());
            if (user.isPresent()) {
                session.setAttribute("firstName", user.get().getFullName());
                if (user.get().getRole().equals("ADMIN")) {
                    return "redirect:/users";
                }
                if (user.get().getRole().equals("CUSTOMER")) {
                    return "redirect:/users/dashboard";
                }
            }
        }
        return "index"; // Your view
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("success", false);
        return "auth/register";
    }

    @PostMapping("/register/submit")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        model.addAttribute("success", false);
        if (result.hasErrors()) { // Check if there are validation errors
            return "auth/register"; // Return to the form page if there are errors
        } else if (existingUser.isPresent()) {
            result.rejectValue("email", "error.user", "Email already in use");
            return "auth/register";
        } else {
            user.setRole("CUSTOMER");
            userService.saveUser(user); // Save the user if no errors
            model.addAttribute("success", true);
            return "auth/register";
        }
    }


    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = getAuthenticationPrincipal();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userService.findUserByEmail(authentication.getName());
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                model.addAttribute("updated", false);
                return "auth/profile";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/profile/update")
    public String updateUser(@Valid User user, BindingResult result, Model model) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (result.hasErrors()) { // Check if there are validation errors
            return "auth/profile"; // Return to the form page if there are errors
        } else if (user.getId() == null) {
            result.rejectValue("id", "error.user", "User Id cannot be null");
            return "auth/profile";
        } else if (existingUser.isPresent() && !user.getId().equals(existingUser.get().getId())) {
            result.rejectValue("email", "error.user", "Email already in use");
            return "auth/profile";
        } else {
            userService.updateProfile(user); // Save the user if no errors
            model.addAttribute("updated", true);
            model.addAttribute("user", user);
            return "auth/profile"; // Redirect to the users page
        }
    }


    public Authentication getAuthenticationPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();

    }
}

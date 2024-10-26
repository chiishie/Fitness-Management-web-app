package com.app.fitnessmanagement.controller;

import com.app.fitnessmanagement.model.User;
import com.app.fitnessmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    @GetMapping
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/create";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable(name = "userId") Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }

    @PostMapping("/create/submit")
    public String addNewUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) { // Check if there are validation errors
            return "admin/create"; // Return to the form page if there are errors
        } else if (userService.findUserByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user", "Email already in use");
            return "admin/create";
        } else {
            userService.saveUser(user); // Save the user if no errors
            return "redirect:/users"; // Redirect to the users page
        }
    }

    @GetMapping("/update/{userId}")
    public String updateUser(@PathVariable(name = "userId") Long userId, Model model) {
        Optional<User> user = userService.findUserById(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "admin/update";
        } else {
            return "redirect:/users";
        }
    }

    @PostMapping("/update/submit")
    public String updateUser(@Valid User user, BindingResult result) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (result.hasErrors()) { // Check if there are validation errors
            return "admin/update"; // Return to the form page if there are errors
        } else if (user.getId() == null) {
            result.rejectValue("id", "error.user", "User Id cannot be null");
            return "admin/update";
        } else if (existingUser.isPresent() && !user.getId().equals(existingUser.get().getId())) {
            result.rejectValue("email", "error.user", "Email already in use");
            return "admin/update";
        } else {
            userService.updateUser(user); // Save the user if no errors
            return "redirect:/users"; // Redirect to the users page
        }
    }
}

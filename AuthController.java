package com.mabentech.controller;

import com.mabentech.model.User;
import com.mabentech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String country,
            RedirectAttributes redirectAttributes) {

        if (userRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already registered. Please sign in.");
            return "redirect:/register";
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .country(country)
                .role(User.Role.CUSTOMER)
                .enabled(true)
                .emailVerified(false)
                .build();

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Account created! Please sign in.");
        return "redirect:/login";
    }
}

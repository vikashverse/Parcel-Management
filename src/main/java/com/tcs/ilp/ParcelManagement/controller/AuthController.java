package com.tcs.ilp.ParcelManagement.controller;

import jakarta.servlet.http.HttpSession;

import com.tcs.ilp.ParcelManagement.model.User;
import com.tcs.ilp.ParcelManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    /** Show login page — redirect to dashboard if already logged in */
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("username") != null) {
            String role = (String) session.getAttribute("role");
            return "OFFICER".equalsIgnoreCase(role) ? "redirect:/officer/home" : "redirect:/home";
        }
        return "login";
    }

    /** Handle registration form submission */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please sign in.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. User ID or email may already exist.");
        }
        return "redirect:/login";
    }

    /** Handle login form submission */
    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        User user = userService.loginUser(email, password);

        if (user != null) {
            session.setAttribute("username", user.getCustomerName());
            session.setAttribute("role",     user.getRole());
            session.setAttribute("userId",   user.getUserId());
            session.setAttribute("email",    user.getEmail());
            session.setAttribute("address",  user.getAddress());
            session.setAttribute("mobile",   user.getMobile());

            return "OFFICER".equalsIgnoreCase(user.getRole())
                    ? "redirect:/officer/home"
                    : "redirect:/home";
        }

        model.addAttribute("error", "Invalid email or password. Please try again.");
        return "login";
    }

    /** Logout — invalidate session and redirect to login */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

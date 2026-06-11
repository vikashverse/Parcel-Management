package com.tcs.ilp.ParcelManagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /** Public landing page */
    @GetMapping("/")
    public String landing(HttpSession session) {
        // If already logged in, redirect to appropriate dashboard
        if (session.getAttribute("username") != null) {
            String role = (String) session.getAttribute("role");
            if ("OFFICER".equalsIgnoreCase(role)) {
                return "redirect:/officer/home";
            }
            return "redirect:/home";
        }
        return "landing";
    }
}

package com.personal.expense.controller;

import com.personal.expense.model.User;
import com.personal.expense.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {

        return "index";
    }
    @GetMapping("/dashboard")
    public String dashboard() {

        return "dashboard";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }


    @GetMapping("/test")
    @ResponseBody
    public String test() {

        return "Controller Working";
    }
    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute User user,
            Model model) {

        if (userService.emailExists(
                user.getEmail())) {

            model.addAttribute(
                    "error",
                    "Email is already registered"
            );

            return "register";
        }

        userService.registerUser(user);

        return "success";
    }
    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        User user =
                userService.loginUser(
                        email,
                        password
                );

        if (user != null) {

            return "dashboard";

        } else {

            model.addAttribute(
                    "error",
                    "Invalid Email or Password"
            );

            return "login";
        }
    }
}
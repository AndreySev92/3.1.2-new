package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String adminHome(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public String getUsers(Model model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("allUsers", list);
        return "all-users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/updateUser")
    public String updateUser(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "add-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        userService.deleteUser(id);
        model.addAttribute("user", user);
        return "redirect:/";
    }

}

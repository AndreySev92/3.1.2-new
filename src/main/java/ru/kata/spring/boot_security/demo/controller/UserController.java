package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUsers(Model model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("allUsers", list);
        return "all-users";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "add-user";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        userService.deleteUser(id);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }


}

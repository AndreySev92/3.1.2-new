package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @PatchMapping("/editUser")
    public String editUser(Model model, @ModelAttribute("userOne") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/saveNewUser")
    public String saveUser(Model model, @ModelAttribute("newUser") @Valid User newUser,
                           BindingResult bindingResult, Principal principal) {
        userValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("newUser", newUser);
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("activeTable", "newUser");
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("thisUser", user);
            List<User> listUsers = userService.getUsers();
            model.addAttribute("users", listUsers);
            model.addAttribute("rolesList", roleService.findAll());
            return "users/allUserBoot";
        }
        userService.save(newUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping
    public String pageBoot(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("thisUser", user);
        List<User> listUsers = userService.getUsers();
        model.addAttribute("users", listUsers);
        model.addAttribute("rolesList", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "users/allUserBoot";
    }
}
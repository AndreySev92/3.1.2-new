package ru.kata.spring.boot_security.demo.init;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Component
public class InDataBaseInitializer {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InDataBaseInitializer(RoleService roleService,
                                 UserService userService,
                                 PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleService.save(roleAdmin);
        roleService.save(roleUser);

        User admin = new User("admin", "adminov", 30, "admin@mail.com",
                passwordEncoder.encode("admin123"), Set.of(roleAdmin, roleUser));
        User user = new User("user", "userov", 25, "user@mail.com",
                passwordEncoder.encode("user123"), Set.of(roleUser));


        userService.save(admin);
        userService.save(user);
    }
}

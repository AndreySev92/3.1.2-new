package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import javax.annotation.PostConstruct;
import java.util.List;


@Configuration
public class inDataBaseInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public inDataBaseInitializer(UserRepository userRepository,
                                 RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);
        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);

        User admin = new User("admin", "admin", 35, "admin@mail.ru", passwordEncoder.encode("100"));
        admin.setRoles(List.of(adminRole, userRole));
        userRepository.save(admin);

        User user = new User("user", "user", 35, "user@mail.ru", passwordEncoder.encode("100"));
        user.setRoles(List.of(userRole));
        userRepository.save(user);
    }
}
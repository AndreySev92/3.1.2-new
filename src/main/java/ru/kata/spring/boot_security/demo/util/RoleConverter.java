package ru.kata.spring.boot_security.demo.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

@Component
public class RoleConverter implements Converter<String, Role> {

    private final RoleService roleService;

    public RoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String name) {
        return roleService.findByName(name).orElse(null);
    }
}

package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kata.spring.boot_security.demo.util.RoleConverter;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final RoleConverter roleConverter;

    public MvcConfig(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleConverter); // 👈 регистрация конвертера
    }
}

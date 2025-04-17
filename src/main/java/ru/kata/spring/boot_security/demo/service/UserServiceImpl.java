package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Внедряем зависимость только для UserRepository
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        // Используем репозиторий для получения всех пользователей
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        // Сохраняем нового пользователя через репозиторий
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        // Получаем пользователя по ID через репозиторий
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        // Удаляем пользователя по ID
        userRepository.deleteById(id);
    }

    @Override
    public void save(User user) {
        // Проверка на уникальность email
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    @Override
    public User findByEmail(String email) {
        // Находим пользователя по email через репозиторий
        return userRepository.findByEmail(email).orElse(null);
    }
}

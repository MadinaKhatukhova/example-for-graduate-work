package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class UserDetailsService implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByEmail(email);
        if (user== null){
            throw new UsernameNotFoundException(email);
        }
        return new UserPrincipal(user);
    }

    @Override
    public void createUser(UserDetails user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);

    }

    @Override
    public void updateUser(UserDetails user) {
        UserEntity userEntity = userRepository.findByEmail(user.getUsername());
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.delete(userEntity);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // В Spring Security этот метод обычно вызывается после аутентификации,
        // поэтому мы можем получить текущего пользователя из SecurityContext
        String currentUsername = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity userEntity = userRepository.findByEmail(currentUsername);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + currentUsername);
        }

        // Проверяем, что старый пароль совпадает
        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Устанавливаем и кодируем новый пароль
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }
}
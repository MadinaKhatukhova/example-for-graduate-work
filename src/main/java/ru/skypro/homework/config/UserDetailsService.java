package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class UserDetailsService implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;



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

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }
}
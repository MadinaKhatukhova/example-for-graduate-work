package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.UserEntity;


@Repository
public interface UserRepository extends JpaRepository <UserEntity, Long> {
    UserEntity findByUserId(Long id);
    UserEntity findByEmail(String str);
}
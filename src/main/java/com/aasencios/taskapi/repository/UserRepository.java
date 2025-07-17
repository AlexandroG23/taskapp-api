package com.aasencios.taskapi.repository;

import com.aasencios.taskapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndEnabledTrue(String email);
    Optional<User> findByEmailAndEnabledFalse(String email);
    List<User> findAllByEnabledTrue();
    List<User> findAllByEnabledFalse();
    List<User> findByEnabledFalse();


}

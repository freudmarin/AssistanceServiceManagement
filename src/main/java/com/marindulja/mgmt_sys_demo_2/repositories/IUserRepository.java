package com.marindulja.mgmt_sys_demo_2.repositories;

import com.marindulja.mgmt_sys_demo_2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

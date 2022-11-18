package com.marindulja.mgmt_sys_demo_2.config;

import com.marindulja.mgmt_sys_demo_2.models.User;
import com.marindulja.mgmt_sys_demo_2.models.UserRole;
import com.marindulja.mgmt_sys_demo_2.repositories.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@Log4j2
public class DataGeneration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final IUserRepository userRepository;

    public DataGeneration(@Qualifier("IUserRepository") IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateData() {
        log.info("Starting to generate admin!");
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder().encode("12345"));
            admin.setFullName("Marin Dulja");
            admin.setRole(UserRole.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}

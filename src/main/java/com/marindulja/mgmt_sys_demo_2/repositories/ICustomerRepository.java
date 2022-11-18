 package com.marindulja.mgmt_sys_demo_2.repositories;



import com.marindulja.mgmt_sys_demo_2.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}

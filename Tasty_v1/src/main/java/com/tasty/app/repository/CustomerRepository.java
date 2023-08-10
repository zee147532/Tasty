package com.tasty.app.repository;

import com.tasty.app.domain.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsernameOrEmail(String username, String email);

    Customer findByUsername(String username);

    Customer findByEmail(String email);

    List<Customer> findAllByProfession_Id(Long id);

    @Modifying
    void deleteByUsername(String username);
}

package com.tasty.app.service;

import com.tasty.app.domain.Customer;

import java.util.List;
import java.util.Optional;

import com.tasty.app.request.ChangePasswordRequest;
import com.tasty.app.service.dto.CustomerDetailDTO;
import com.tasty.app.service.dto.CustomerDTO;
import com.tasty.app.service.dto.FileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * Service Interface for managing {@link Customer}.
 */
public interface CustomerService {
    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    Customer save(Customer customer);

    /**
     * Updates a customer.
     *
     * @param customer the entity to update.
     * @return the persisted entity.
     */
    Customer update(Customer customer);

    /**
     * Partially updates a customer.
     *
     * @param customer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Customer> partialUpdate(Customer customer);

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Customer> findAll(Pageable pageable);

    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Customer> findOne(Long id);

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Customer> findAll();

    String createCustomer(CustomerDTO dto);

    ResponseEntity updateCustomer(CustomerDetailDTO dto);

    String deleteCustomer(String username);

    ResponseEntity getCustomerProfile(String username);

    ResponseEntity getCustomerDetail(String username);

    ResponseEntity changePassword(ChangePasswordRequest request);

    ResponseEntity getAvatar(String username);

    ResponseEntity updateAvatar(FileDTO dto, String username);

    String encodePassword(String password);
}

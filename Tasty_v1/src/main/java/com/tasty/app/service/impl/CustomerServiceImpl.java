package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Profession;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.FavoritesRepository;
import com.tasty.app.repository.ProfessionRepository;
import com.tasty.app.service.CustomerService;

import java.util.List;
import java.util.Optional;

import com.tasty.app.service.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final ProfessionRepository professionRepository;
    private final FavoritesRepository favoritesRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ProfessionRepository professionRepository, FavoritesRepository favoritesRepository) {
        this.customerRepository = customerRepository;
        this.professionRepository = professionRepository;
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        log.debug("Request to update Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getUsername() != null) {
                    existingCustomer.setUsername(customer.getUsername());
                }
                if (customer.getPassword() != null) {
                    existingCustomer.setPassword(customer.getPassword());
                }
                if (customer.getFullName() != null) {
                    existingCustomer.setFullName(customer.getFullName());
                }
                if (customer.getPhoneNumber() != null) {
                    existingCustomer.setPhoneNumber(customer.getPhoneNumber());
                }
                if (customer.getEmail() != null) {
                    existingCustomer.setEmail(customer.getEmail());
                }
                if (customer.getStatus() != null) {
                    existingCustomer.setStatus(customer.getStatus());
                }
                if (customer.getGender() != null) {
                    existingCustomer.setGender(customer.getGender());
                }
                if (customer.getConfirmed() != null) {
                    existingCustomer.setConfirmed(customer.getConfirmed());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public String createCustomer(CustomerDTO dto) {
        // TODO: Mã hóa mật khẩu
        String encodedPassword = dto.getPassword();

        Profession profession = professionRepository.getReferenceById(dto.getProfessionId());
        Customer customer = new Customer()
            .username(dto.getUsername())
            .password(encodedPassword)
            .fullName(dto.getFullName())
            .phoneNumber(dto.getPhoneNumber())
            .email(dto.getEmail())
            .status(true)
            .gender(dto.getGender())
            .confirmed(dto.getConfirmed())
            .profession(profession);

        customerRepository.save(customer);
        return "Success.";
    }

    @Override
    public String updateCustomer(CustomerDTO dto) {
        // TODO: Mã hóa mật khẩu
        String encodedPassword = dto.getPassword();

        Profession profession = professionRepository.getReferenceById(dto.getProfessionId());
        Customer customer = customerRepository.findByUsername(dto.getUsername());
        customer.password(encodedPassword)
            .fullName(dto.getFullName())
            .phoneNumber(dto.getPhoneNumber())
            .email(dto.getEmail())
            .status(true)
            .gender(dto.getGender())
            .confirmed(dto.getConfirmed())
            .profession(profession);

        customerRepository.save(customer);
        return "Success.";
    }

    @Override
    public String deleteCustomer(String username) {
        favoritesRepository.deleteAllByCustomer_Username(username);
        customerRepository.deleteByUsername(username);
        return "Success.";
    }
}

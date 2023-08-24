package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Image;
import com.tasty.app.domain.Profession;
import com.tasty.app.repository.*;
import com.tasty.app.request.CustomerRequest;
import com.tasty.app.response.CustomerProfileResponse;
import com.tasty.app.security.jwt.TokenProvider;
import com.tasty.app.service.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.tasty.app.service.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static com.tasty.app.constant.Constant.AUTHORIZATION;
import static com.tasty.app.domain.enumeration.TypeOfImage.CUSTOMER;

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
    private final HttpServletRequest servletRequest;
    private final TokenProvider tokenProvider;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ProfessionRepository professionRepository, FavoritesRepository favoritesRepository, HttpServletRequest servletRequest, TokenProvider tokenProvider, ImageRepository imageRepository, PostRepository postRepository) {
        this.customerRepository = customerRepository;
        this.professionRepository = professionRepository;
        this.favoritesRepository = favoritesRepository;
        this.servletRequest = servletRequest;
        this.tokenProvider = tokenProvider;
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
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
    public ResponseEntity updateCustomer(CustomerRequest request) {
        String token = servletRequest.getHeader(AUTHORIZATION).substring(7);
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("errorMsg","Phiên đăng nhập đã hết. Vui lòng đăng nhập lại."));
        }
        String username = tokenProvider.getAuthentication(token).getName();
        if (!username.equals(request.getUsername())) {
            return ResponseEntity.status(403).body(Map.of("errorMsg","Bạn không có quyền chỉnh sửa thông tin của người dùng này."));
        }
        Profession profession = professionRepository.getReferenceById(request.getProfessionId());
        Customer customer = customerRepository.findByUsername(request.getUsername());
        customer.fullName(request.getFullName())
            .phoneNumber(request.getPhoneNumber())
            .status(true)
            .gender(request.getGender())
            .profession(profession)
            .description(request.getDescription());

        customerRepository.save(customer);
        return ResponseEntity.ok("Success.");
    }

    @Override
    public String deleteCustomer(String username) {
        favoritesRepository.deleteAllByCustomer_Username(username);
        customerRepository.deleteByUsername(username);
        return "Success.";
    }

    @Override
    public ResponseEntity getCustomerProfile(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (Objects.isNull(customer)) {
            return ResponseEntity.status(400).body(Map.of("errorMsg", String.format("Không tìm thấy người dùng %s", customer)));
        }

        Image image = imageRepository.findByTypeAndCustomer(CUSTOMER, customer);

        Long totalPosts = postRepository.countAllByAuthor_Username(customer.getUsername());

        CustomerProfileResponse response = new CustomerProfileResponse(
            customer.getId(),
            customer.getUsername(),
            customer.getFullName(),
            customer.getGender(),
            customer.getDescription(),
            Objects.isNull(image) ? "" : image.getUri(),
            totalPosts
        );

        return ResponseEntity.ok(response);
    }
}

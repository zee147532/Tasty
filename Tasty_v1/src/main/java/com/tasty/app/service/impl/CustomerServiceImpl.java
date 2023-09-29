package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Image;
import com.tasty.app.domain.Profession;
import com.tasty.app.repository.*;
import com.tasty.app.request.ChangePasswordRequest;
import com.tasty.app.service.ImageService;
import com.tasty.app.service.dto.CustomerDetailDTO;
import com.tasty.app.response.CustomerProfileResponse;
import com.tasty.app.security.jwt.TokenProvider;
import com.tasty.app.service.CustomerService;

import java.util.*;

import com.tasty.app.service.dto.CustomerDTO;
import com.tasty.app.service.dto.FileDTO;
import com.tasty.app.service.dto.ImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final MinioService minioService;
    private final ImageService imageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, ProfessionRepository professionRepository, FavoritesRepository favoritesRepository, HttpServletRequest servletRequest, TokenProvider tokenProvider, ImageRepository imageRepository, PostRepository postRepository, MinioService minioService, ImageService imageService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.professionRepository = professionRepository;
        this.favoritesRepository = favoritesRepository;
        this.servletRequest = servletRequest;
        this.tokenProvider = tokenProvider;
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
        this.minioService = minioService;
        this.imageService = imageService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    public ResponseEntity updateCustomer(CustomerDetailDTO dto) {
        String token = servletRequest.getHeader(AUTHORIZATION).substring(7);
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("errorMsg","Phiên đăng nhập đã hết. Vui lòng đăng nhập lại."));
        }
        String username = tokenProvider.getAuthentication(token).getName();
        if (!username.equals(dto.getUsername())) {
            return ResponseEntity.status(403).body(Map.of("errorMsg","Bạn không có quyền chỉnh sửa thông tin của người dùng này."));
        }
        Profession profession = null;
        if (dto.getProfessionId() != 0L) {
            profession = professionRepository.getReferenceById(dto.getProfessionId());
        }
        Customer customer = customerRepository.findByUsername(dto.getUsername());
        customer.fullName(dto.getFullName())
            .phoneNumber(dto.getPhoneNumber())
            .status(true)
            .gender(dto.getGender())
            .profession(profession)
            .description(dto.getDescription());

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

    @Override
    public ResponseEntity getCustomerDetail(String username) {
        String token = servletRequest.getHeader(AUTHORIZATION).substring(7);
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("errorMsg","Phiên đăng nhập đã hết. Vui lòng đăng nhập lại."));
        }
        String loggedUsername = tokenProvider.getAuthentication(token).getName();
        if (!loggedUsername.equals(username)) {
            return ResponseEntity.status(403).body(Map.of("errorMsg","Bạn không có quyền xem thông tin của người dùng này."));
        }
        Customer customer = customerRepository.findByUsername(username);
        if (Objects.isNull(customer)) {
            return ResponseEntity.status(400).body(Map.of("errorMsg", String.format("Không thể tìm thấy người dùng %s", username)));
        }

        Image image = imageRepository.findByTypeAndCustomer(CUSTOMER, customer);

        CustomerDetailDTO response = new CustomerDetailDTO(
            customer.getId(),
            customer.getUsername(),
            customer.getFullName(),
            customer.getPhoneNumber(),
            customer.getEmail(),
            customer.getGender(),
            Objects.isNull(customer.getProfession()) ? 0 : customer.getProfession().getId(),
            customer.getDescription(),
            Objects.isNull(image) ? "" : image.getUri()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity changePassword(ChangePasswordRequest request) {
        String token = servletRequest.getHeader(AUTHORIZATION).substring(7);
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("errorMsg","Phiên đăng nhập đã hết. Vui lòng đăng nhập lại."));
        }
        String loggedUsername = tokenProvider.getAuthentication(token).getName();
        if (!loggedUsername.equals(request.getUsername())) {
            return ResponseEntity.status(403).body(Map.of("errorMsg","Bạn không có quyền thay đổi mật khẩu của người dùng này."));
        }
        Customer customer = customerRepository.findByUsername(request.getUsername());
        if (Objects.isNull(customer)) {
            return ResponseEntity.status(400).body(Map.of("errorMsg", String.format("Không thể tìm thấy người dùng %s", request.getUsername())));
        }

        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), customer.getPassword())) {
            return ResponseEntity.status(400).body(Map.of("errorMsg", "Mật khẩu không chính xác."));
        }

        customer.setPassword(encodePassword(request.getNewPassword()));
        customerRepository.save(customer);
        return ResponseEntity.ok("Success");
    }

    public ResponseEntity getAvatar(String username) {
        Image image = imageRepository.findByTypeAndCustomer_Username(CUSTOMER, username);
        Map response = new HashMap<>();
        try {
            response.put("url", image.getUri());
        } catch (NullPointerException e) {
            response.put("url", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSheI9UkWllIpSNbs2UdE18KLLswgDON9qzXg&usqp=CAU");
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity updateAvatar(FileDTO dto, String username) {
        String token = servletRequest.getHeader(AUTHORIZATION).substring(7);
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("errorMsg","Phiên đăng nhập đã hết. Vui lòng đăng nhập lại."));
        }
        String loggedUsername = tokenProvider.getAuthentication(token).getName();
        if (!loggedUsername.equals(username)) {
            return ResponseEntity.status(403).body(Map.of("errorMsg","Bạn không có quyền chỉnh sửa thông tin của người dùng này."));
        }

        String imageUrl = minioService.uploadFile(dto);
        imageRepository.deleteAllByCustomer_UsernameAndType(username, CUSTOMER);
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUsername(username);
        imageDTO.setType(CUSTOMER);
        imageDTO.setUri(imageUrl);
        imageService.createImage(imageDTO);
        return ResponseEntity.ok("Success.");
    }

    @Override
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}

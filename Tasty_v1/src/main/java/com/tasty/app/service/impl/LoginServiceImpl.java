package com.tasty.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Profession;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.ProfessionRepository;
import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;
import com.tasty.app.response.HttpResponse;
import com.tasty.app.response.InfoResponse;
import com.tasty.app.response.RegistryResponse;
import com.tasty.app.security.jwt.TokenProvider;
import com.tasty.app.service.LoginService;
import com.tasty.app.service.MailService;
import com.tasty.app.service.dto.CustomerDetail;
import com.tasty.app.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    private final CustomerRepository customerRepository;
    private final ProfessionRepository professionRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final Duration PERMISSION_CACHE_TTL = Duration.ofMinutes(5);
    private final ObjectMapper mapper;
    private final TokenProvider tokenProvider;
    private final MailService mailService;

    public LoginServiceImpl(ObjectMapper mapper,
                            CustomerRepository customerRepository,
                            ProfessionRepository professionRepository,
                            RedisTemplate<String, String> redisTemplate,
                            TokenProvider tokenProvider, MailService mailService) {
        this.customerRepository = customerRepository;
        this.professionRepository = professionRepository;
        this.redisTemplate = redisTemplate;
        this.mapper = mapper;
        this.tokenProvider = tokenProvider;
        this.mailService = mailService;
    }

    public Map<String, String> customerLogin(CustomerDetail customerDetail) {
        Customer customer = customerRepository.findByUsername(customerDetail.getUsername());
        if (Objects.isNull(customer)) {
            throw new BadRequestAlertException("Tên đăng nhập hoặc mật khẩu không chính xác.", "customer", "customernotfound");
        }
        if (!customerDetail.getPassword().equals(customer.getPassword())) {
            throw new BadRequestAlertException("Tên đăng nhập hoặc mật khẩu không chính xác.", "customer", "customernotfound");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            customerDetail.getUsername(),
            customerDetail.getPassword()
        );
        String token = tokenProvider.createToken(authenticationToken, true);

        Map<String, String> result = new HashMap<>();
        result.put("jwtToken", token);
        result.put("username", customer.getUsername());
        return result;
    }

    @Override
    public RegistryResponse registry(RegistryRequest request) {
        String username = request.getUsername();
        RegistryResponse response = new RegistryResponse();
        if (Objects.nonNull(customerRepository.findByUsername(username))) {
            response.setErrorMsg("Tên đăng nhập đã được sử dụng.");
            response.setStatusCode(400);
            return response;
        }
        // TODO: Encode mật khẩu
        String encodedPassword = request.getPassword();
        Customer customer = new Customer().username(username)
            .password(encodedPassword)
            .confirmed(false);
        customerRepository.save(customer);
        response.setUsername(username);
        response.setStatusCode(200);
        return response;
    }

    @Override
    public InfoResponse fillInfo(InfoRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();
        InfoResponse response = new InfoResponse();
        response.setEmail(email);
        if (Objects.nonNull(customerRepository.findByEmail(email))) {
            response.setErrorMsg("Địa chỉ email đã được sử dụng.");
            response.setStatusCode(400);
            return response;
        }

        try {
            sendCode(email);
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setErrorMsg("Không thể gửi mã xác thực đến email của bạn, vui lòng thử lại.");
            return response;
        }

        Profession profession = professionRepository.getReferenceById(request.getProfession());
        Customer customer = customerRepository.findByUsername(username)
            .fullName(request.getFullName())
            .phoneNumber(request.getPhoneNumber())
            .email(email)
            .gender(request.getGender())
            .profession(profession)
            .status(true)
            .confirmed(false);

        customerRepository.save(customer);
        response.setStatusCode(200);
        return response;
    }

    public HttpResponse verify(VerifyRequest request) {
        // TODO: Lấy mã xác thực đúng từ redis sử dung email
        String correctCode = mapper.convertValue(redisTemplate.opsForValue().get(request.getEmail()), String.class);
        HttpResponse response = new HttpResponse();

        if (correctCode.equals(request.getVerifyCode())) {
            Customer customer = customerRepository.findByEmail(request.getEmail());
            customer.setConfirmed(true);
            customerRepository.save(customer);
            response.setStatusCode(200);
            response.setMsg("Success.");
            return response;
        }

        response.setStatusCode(400);
        response.setMsg("Mã xác thực không chính xác. Vui lòng thử lại.");
        return response;
    }

    public String sendCode(String email) {
        // Xóa mã theo email trên redis
        redisTemplate.delete(email);

        // Tạo mã xác nhận và lưu vào redis cùng email với thời gian tồn tại là 5 phút
        String generatedCode = RandomStringUtils.random(6, false, true);
        redisTemplate.opsForValue().set(email, generatedCode, PERMISSION_CACHE_TTL);

        // Gửi mail xác nhận
        mailService.sendVerificationEmail(email, generatedCode);

        return "Success.";
    }
}

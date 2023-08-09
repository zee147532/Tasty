package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Profession;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.ProfessionRepository;
import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;
import com.tasty.app.service.LoginService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProfessionRepository professionRepository;
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
    private static final Duration PERMISSION_CACHE_TTL = Duration.ofMinutes(5);

    @Override
    public String registry(RegistryRequest request) {
        String username = request.getUsername();
        if (Objects.nonNull(customerRepository.findByUsername(username))) {
            return "Fail.";
        }
        // TODO: Encode mật khẩu
        String encodedPassword = request.getPassword();
        Customer customer = new Customer().username(username)
            .password(encodedPassword);
        customerRepository.save(customer);
        return "Success.";
    }

    @Override
    public String fillInfo(InfoRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();
        if (Objects.nonNull(customerRepository.findByEmail(email))) {
            return "Fail.";
        }
        Profession profession = professionRepository.getReferenceById(request.getProfessionId());
        Customer customer = customerRepository.findByUsername(username)
            .fullName(request.getFullName())
            .phoneNumber(request.getPhoneNumber())
            .email(email)
            .gender(request.getGender())
            .profession(profession)
            .status(true)
            .confirmed(false);

        customerRepository.save(customer);

        // TODO: Tạo mã xác nhận và lưu vào redis cùng email với thời gian tồn tại là 5 phút
        int leftLimit = 48; // Số 0
        int rightLimit = 57; // Số 9
        int targetStringLength = 6;
        Random random = new Random();

//        String generatedCode = random.ints(leftLimit, rightLimit + 1)
//            .li
        String generatedCode = RandomStringUtils.random(6,false,true);
//        redisTemplate.opsForValue().set(email, generatedCode, PERMISSION_CACHE_TTL);

        // TODO: Gửi mail xác nhận

        return "Success.";
    }

    public String verify(VerifyRequest request) {
        // TODO: Lấy mã xác thực đúng từ redis sử dung email
        String correctCode = "";

        if (correctCode.equals(request.getVerifyCode())) {
            Customer customer = customerRepository.findByEmail(request.getEmail());
            customer.setConfirmed(true);
            customerRepository.save(customer);
            return "Success.";
        }

        return "Fail.";
    }

    public String resendCode(String email) {
        // TODO: Xóa mã theo email trên redis
        // TODO: Tạo mã xác nhận và lưu vào redis cùng email với thời gian tồn tại là 5 phút
        // TODO: Gửi mail xác nhận
        return "Success.";
    }
}

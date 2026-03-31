package com.btl.snaker.service;

import com.btl.snaker.entity.User;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.repository.UserRepository;
import com.btl.snaker.service.imp.MailServiceImp;
import com.btl.snaker.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class MailService implements MailServiceImp {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailUtil mailUtil;

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateResetCode(String email) {
        Random random = new Random();
        String resetCode = String.format("%06d", random.nextInt(1000000));

        User user = userRepository.findByEmail(email);
        if(user != null) {
            user.setVerifyCode(resetCode);
            user.setCreateCodeAt(Timestamp.valueOf(LocalDateTime.now()));
            userRepository.save(user);
        }
        return resetCode;
    }

    @Override
    public ResponseData validateCode(String email, String resetCode) {
        ResponseData responseData = new ResponseData();

        User user = userRepository.findByEmail(email);
        if(user != null) {
            if(user.getVerifyCode().equals(resetCode)){
                Timestamp createCodeAt = user.getCreateCodeAt();
                if (createCodeAt != null && createCodeAt.toLocalDateTime().plusMinutes(10).isAfter(LocalDateTime.now())) {
                    responseData.setSuccess(true);
                    responseData.setDescription("Success");
                } else {
                    responseData.setSuccess(false);
                    responseData.setDescription("Expired");
                }
            } else {
                responseData.setSuccess(false);
                responseData.setDescription("Invalid code");
            }
        }
        return responseData;
    }

    @Async
    public ResponseData sentForgotPasswordMail(String fullname, String toAddress, String verifyCode) {
        ResponseData responseData = new ResponseData();
        User user = userRepository.findByEmail(toAddress);
        String subject = "Mã xác nhận đặt lại mật khẩu ShoesShop";
        String content = """
            Xin chào [[name]],<br>
            Đây là mã xác thực tạm thời để đặt lại mật khẩu của bạn. Vui lòng không chia sẻ mã này!<br>
            <h4>Mã xác thực: [[verifyCode]]</h4><br>
            Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.<br>
            [[Company]]
            """;
        content = content.replace("[[name]]", fullname);
        content = content.replace("[[verifyCode]]", verifyCode);
        content = content.replace("[[Company]]", "Khánh Sky");

        boolean isSent = mailUtil.sendEmail(toAddress, subject, content);

        if(isSent){
            user.setCreateCodeAt(Timestamp.valueOf(LocalDateTime.now()));
            user.setVerifyCode(verifyCode);
            userRepository.save(user);

            responseData.setSuccess(true);
            responseData.setDescription("Success");
        }
        else {
            responseData.setSuccess(false);
            responseData.setDescription("Failed to send email");
        }
        return responseData;
    }
}

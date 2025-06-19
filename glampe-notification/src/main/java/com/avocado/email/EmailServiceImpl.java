package com.avocado.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(EmailEntity email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            context.setVariable("OTP_CODE", "000000");
            context.setVariable("EXPIRY_MINUTES", 20);
            context.setVariable("YEAR", LocalDate.now().getYear());
            String html = templateEngine.process("otp.html", context);
            helper.setText(html, true);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setFrom(from);

            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

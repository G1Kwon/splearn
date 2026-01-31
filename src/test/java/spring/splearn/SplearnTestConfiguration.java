package spring.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import spring.splearn.application.member.required.EmailSender;
import spring.splearn.domain.member.MemberFixture;
import spring.splearn.domain.member.PasswordEncoder;

@TestConfiguration
public class SplearnTestConfiguration {

  @Bean
  public EmailSender emailSender() {
    return (email, subject, body) -> System.out.println("Sending email: " + email);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return MemberFixture.createPasswordEncoder();
  }
}

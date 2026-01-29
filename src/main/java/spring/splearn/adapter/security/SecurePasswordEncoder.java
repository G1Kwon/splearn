package spring.splearn.adapter.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import spring.splearn.domain.PasswordEncoder;

@Component
public class SecurePasswordEncoder implements PasswordEncoder {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public String encode(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  @Override
  public boolean matches(String Password, String passwordHash) {
    return bCryptPasswordEncoder.matches(Password, passwordHash);
  }
}

package spring.splearn.domain;

public interface PasswordEncoder {

  String encode(String password);

  boolean matches(String Password, String passwordHash);

}

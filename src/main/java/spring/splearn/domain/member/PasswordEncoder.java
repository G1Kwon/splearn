package spring.splearn.domain.member;

public interface PasswordEncoder {

  String encode(String password);

  boolean matches(String Password, String passwordHash);

}

package spring.splearn.domain.member;

public class MemberFixture {

  public static MemberRegisterRequest createMemberRegisterRequest(String email) {
    return new MemberRegisterRequest(email, "Charlie", "verysecret");
  }

  public static MemberRegisterRequest createMemberRegisterRequest() {
    return createMemberRegisterRequest("g1@splearn.app");
  }

  public static PasswordEncoder createPasswordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(String password) {
        return password.toUpperCase();
      }

      @Override
      public boolean matches(String Password, String passwordHash) {
        return encode(Password).equals(passwordHash);
      }
    };
  }
}

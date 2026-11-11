package spring.splearn.domain.member;

import org.springframework.test.util.ReflectionTestUtils;
import spring.splearn.application.member.provided.MemberRegisterRequest;

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

  public static Member createMember() {
    return Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
  }

  public static Member createMember(Long id) {
    Member member = Member.register(createMemberRegisterRequest().toInfo(),
        createPasswordEncoder());
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }

  public static Member createMember(String email) {
    return Member.register(createMemberRegisterRequest(email).toInfo(), createPasswordEncoder());
  }

  public static Member createActiveMember() {
    Member member = createMember();
    member.activate();
    return member;
  }
}

package spring.splearn.application.provided;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import spring.splearn.SplearnTestConfiguration;
import spring.splearn.application.member.provided.LoginFailedException;
import spring.splearn.application.member.provided.MemberAuthenticater;
import spring.splearn.application.member.provided.MemberLoginRequest;
import spring.splearn.application.member.provided.MemberRegister;
import spring.splearn.domain.member.MemberFixture;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
class MemberAuthenticatorTest {

  @Autowired
  private MemberAuthenticater memberAuthenticater;

  @Autowired
  private MemberRegister memberRegister;

  @Test
  void login() {
    var registerRequest = MemberFixture.createMemberRegisterRequest();
    memberRegister.register(registerRequest).activate();

    var member = memberAuthenticater.login(new MemberLoginRequest(
        registerRequest.email(),
        registerRequest.password()
    ));
  }

  @Test
  void loginFailedNotActive() {
    var registerRequest = MemberFixture.createMemberRegisterRequest();
    memberRegister.register(registerRequest);

    Assertions.assertThatThrownBy(() ->
        memberAuthenticater.login(new MemberLoginRequest(
            registerRequest.email(),
            registerRequest.password()
        ))).isInstanceOf(LoginFailedException.class);
  }

  @Test
  void loginFailedEmailNotExist() {
    var registerRequest = MemberFixture.createMemberRegisterRequest();
    memberRegister.register(registerRequest);

    Assertions.assertThatThrownBy(() ->
        memberAuthenticater.login(new MemberLoginRequest(
            "notexist@email.com",
            registerRequest.password()
        ))).isInstanceOf(LoginFailedException.class);
  }

  @Test
  void loginFailedWrongPassword() {
    var registerRequest = MemberFixture.createMemberRegisterRequest();
    memberRegister.register(registerRequest);

    Assertions.assertThatThrownBy(() ->
        memberAuthenticater.login(new MemberLoginRequest(
            registerRequest.email(),
            "00000000"
        ))).isInstanceOf(LoginFailedException.class);
  }
}

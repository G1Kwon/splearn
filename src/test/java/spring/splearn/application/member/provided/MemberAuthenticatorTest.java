package spring.splearn.application.member.provided;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import spring.splearn.SplearnTestConfiguration;
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
    var member = memberRegister.register(registerRequest);
    member.activate();

    var loggedInMember = memberAuthenticater.login(new MemberLoginRequest(
        registerRequest.email(),
        registerRequest.password()
    ));

    assertThat(loggedInMember.getId()).isEqualTo(member.getId());
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

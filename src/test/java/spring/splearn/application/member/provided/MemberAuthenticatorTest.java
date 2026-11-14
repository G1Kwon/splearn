package spring.splearn.application.member.provided;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.splearn.application.support.stereotype.ApplicationServiceTest;
import spring.splearn.domain.member.MemberFixture;

@ApplicationServiceTest
@RequiredArgsConstructor
class MemberAuthenticatorTest {

  final MemberAuthenticater memberAuthenticater;
  final MemberRegister memberRegister;

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

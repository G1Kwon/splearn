package spring.splearn.application.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.splearn.SplearnTestConfiguration;
import spring.splearn.domain.DuplicateEmailException;
import spring.splearn.domain.Member;
import spring.splearn.domain.MemberFixture;
import spring.splearn.domain.MemberRegisterRequest;
import spring.splearn.domain.MemberStatus;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public record MemberRegisterTest(MemberRegister memberRegister) {

  @Test
  void register() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void duplicateEmailFail() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
        .isInstanceOf(DuplicateEmailException.class);

  }

  @Test
  void memberRegisterRequestFail() {
    extracted(new MemberRegisterRequest("g1@splearn.app", "g1", "secret"));
    extracted(new MemberRegisterRequest("g1@splearn.app", "Charlie_____________________________",
        "longsecret"));
    extracted(new MemberRegisterRequest("g1splearn.app", "g1__________________", "longsecret"));

  }

  private void extracted(MemberRegisterRequest invalid) {
    assertThatThrownBy(() -> memberRegister.register(invalid))
        .isInstanceOf(ConstraintViolationException.class);
  }

}

package spring.splearn.application.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
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
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

  @Test
  void register() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void duplicateEmailFail() {
    memberRegister.register(MemberFixture.createMemberRegisterRequest());

    assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
        .isInstanceOf(DuplicateEmailException.class);

  }

  @Test
  void activate() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    entityManager.flush();
    entityManager.clear();

    member = memberRegister.activate(member.getId());

    entityManager.flush();

    assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void memberRegisterRequestFail() {
    checkValidation(new MemberRegisterRequest("g1@splearn.app", "g1", "secret"));
    checkValidation(
        new MemberRegisterRequest("g1@splearn.app", "Charlie_____________________________",
            "longsecret"));
    checkValidation(
        new MemberRegisterRequest("g1splearn.app", "g1__________________", "longsecret"));

  }

  private void checkValidation(MemberRegisterRequest invalid) {
    assertThatThrownBy(() -> memberRegister.register(invalid))
        .isInstanceOf(ConstraintViolationException.class);
  }

}

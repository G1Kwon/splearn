package spring.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MemberTest {

  @Test
  void createMember() {
    var member = new Member("g1@splearn.app", "g1", "secret");

    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void constructorNullCheck() {
    assertThatThrownBy(() -> new Member(null, "g1", "secret"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void activate() {
    var member = new Member("g1@splearn.app", "g1", "secret");

    member.activate();

    assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void activateFail() {
    var member = new Member("g1@splearn.app", "g1", "secret");

    member.activate();

    assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void deactivate() {
    var member = new Member("g1@splearn.app", "g1", "secret");

    member.activate();
    member.deactivate();

    assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
  }

  @Test
  void deactivateFail() {
    var member = new Member("g1@splearn.app", "g1", "secret");

    assertThatThrownBy(() -> {
      member.deactivate();
    }).isInstanceOf(IllegalStateException.class);

    member.activate();
    member.deactivate();

    assertThatThrownBy(() -> {
      member.deactivate();
    }).isInstanceOf(IllegalStateException.class);
  }
}
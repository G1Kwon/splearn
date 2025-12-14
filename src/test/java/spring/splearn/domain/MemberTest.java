package spring.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MemberTest {

  @Test
  void createMember() {
    var member = new Member("g1@splearn.app", "g1", "secret");

    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

}
package spring.splearn.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProfileTest {

  @Test
  void profile() {
    new Profile("g1kwon");
    new Profile("12345");
    new Profile("abcdefghijk");

  }

  @Test
  void profileFail() {
    assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolong")).isInstanceOf(
        IllegalArgumentException.class);
    assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void url() {
    var profile = new Profile("g1kwon");

    assertThat(profile.url()).isEqualTo("@g1kwon");
  }
}
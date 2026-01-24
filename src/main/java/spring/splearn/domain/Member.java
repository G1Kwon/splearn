package spring.splearn.domain;

import static org.springframework.util.Assert.state;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Member {

  private String email;
  private String nickname;
  private String passwordHash;
  private MemberStatus status;

  private Member() {

  }

  //생성자 대체하는 정적 팩토리 메서드
  //새로운 속성이 들어가도 파라미터 리스트가 길어지지 않는다.
  public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
    Member member = new Member();

    member.email = Objects.requireNonNull(createRequest.email());
    member.nickname = Objects.requireNonNull(createRequest.nickname());
    member.passwordHash = Objects.requireNonNull(
        passwordEncoder.encode(createRequest.passwordHash()));

    member.status = MemberStatus.PENDING;

    return member;
  }

  public void activate() {
    state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");

    this.status = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

    this.status = MemberStatus.DEACTIVATED;
  }

  public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, this.passwordHash);
  }

  public void changeNickName(String nickname) {
    this.nickname = Objects.requireNonNull(nickname);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
  }

  public boolean isActive() {
    return this.status == MemberStatus.ACTIVE;
  }
}

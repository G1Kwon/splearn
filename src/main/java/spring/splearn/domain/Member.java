package spring.splearn.domain;

import static org.springframework.util.Assert.state;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  @NaturalId
  private Email email;
  private String nickname;
  private String passwordHash;
  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  //생성자 대체하는 정적 팩토리 메서드
  //새로운 속성이 들어가도 파라미터 리스트가 길어지지 않는다.
  public static Member register(MemberRegisterRequest createRequest,
      PasswordEncoder passwordEncoder) {
    Member member = new Member();

    member.email = new Email(Objects.requireNonNull(createRequest.email()));
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

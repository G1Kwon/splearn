package spring.splearn.domain.member;

import static org.springframework.util.Assert.state;

import jakarta.persistence.Entity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import spring.splearn.domain.AbstractEntity;
import spring.splearn.domain.shared.Email;

@Entity
//xml 에서 설정 가능
//@Table(name = "MEMBER", uniqueConstraints = {
//    @UniqueConstraint(name = "UK_MEMBER_EMAIL_ADDRESS", columnNames = "email_address")
//})
@Getter
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@NaturalIdCache
public class Member extends AbstractEntity {

  //  @Embedded
  @NaturalId
  private Email email;
  //  @Column(length = 100, nullable = false)
  private String nickname;
  //  @Column(length = 200, nullable = false)
  private String passwordHash;
  //  @Enumerated(EnumType.STRING)
//  @Column(length = 50, nullable = false)
  private MemberStatus status;

  //  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private MemberDetail detail;

  //생성자 대체하는 정적 팩토리 메서드
  //새로운 속성이 들어가도 파라미터 리스트가 길어지지 않는다.
  public static Member register(MemberRegisterRequest createRequest,
      PasswordEncoder passwordEncoder) {
    Member member = new Member();

    member.email = new Email(Objects.requireNonNull(createRequest.email()));
    member.nickname = Objects.requireNonNull(createRequest.nickname());
    member.passwordHash = Objects.requireNonNull(
        passwordEncoder.encode(createRequest.password()));

    member.status = MemberStatus.PENDING;

    member.detail = MemberDetail.create();

    return member;
  }

  public void activate() {
    state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");

    this.status = MemberStatus.ACTIVE;
    this.detail.activate();
  }

  public void deactivate() {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

    this.status = MemberStatus.DEACTIVATED;
    this.detail.deactivate();
  }

  public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, this.passwordHash);
  }

  public void changeNickName(String nickname) {
    this.nickname = Objects.requireNonNull(nickname);
  }

  public void updateInfo(MemberInfoUpdateRequest updateRequest) {
    this.nickname = Objects.requireNonNull(updateRequest.nickname());

    this.detail.updateInfo(updateRequest);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
  }

  public boolean isActive() {
    return this.status == MemberStatus.ACTIVE;
  }
}

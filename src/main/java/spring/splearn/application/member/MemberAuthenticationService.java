package spring.splearn.application.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import spring.splearn.application.member.provided.LoginFailedException;
import spring.splearn.application.member.provided.MemberAuthenticater;
import spring.splearn.application.member.provided.MemberLoginRequest;
import spring.splearn.application.member.required.MemberRepository;
import spring.splearn.domain.member.Member;
import spring.splearn.domain.member.PasswordEncoder;
import spring.splearn.domain.shared.Email;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberAuthenticationService implements MemberAuthenticater {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Member login(MemberLoginRequest loginRequest) throws LoginFailedException {
    Member member = memberRepository.findByEmail(
        new Email(loginRequest.email())).orElseThrow(
        LoginFailedException::new
    );

    if (!member.isActive()) {
      throw new LoginFailedException();
    }
    
    if (!member.verifyPassword(loginRequest.password(), passwordEncoder)) {
      throw new LoginFailedException();
    }

    return member;
  }
}

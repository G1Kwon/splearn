package spring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import spring.splearn.application.provided.MemberRegister;
import spring.splearn.application.required.EmailSender;
import spring.splearn.application.required.MemberRepository;
import spring.splearn.domain.DuplicateEmailException;
import spring.splearn.domain.Email;
import spring.splearn.domain.Member;
import spring.splearn.domain.MemberRegisterRequest;
import spring.splearn.domain.PasswordEncoder;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

  private final MemberRepository memberRepository;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Member register(MemberRegisterRequest registerRequest) {
    // check
    checkDuplicateEmail(registerRequest);

    // domain model
    Member member = Member.register(registerRequest, passwordEncoder);

    //repository
    memberRepository.save(member);

    //post process
    sendWelcomeEmail(member);

    return member;
  }

  private void sendWelcomeEmail(Member member) {
    emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
  }

  private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
    if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
      throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
    }
  }
}

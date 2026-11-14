package spring.splearn.application.member;

import lombok.RequiredArgsConstructor;
import spring.splearn.application.member.provided.MemberFinder;
import spring.splearn.application.member.required.MemberRepository;
import spring.splearn.domain.member.Member;
import spring.splearn.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {

  private final MemberRepository memberRepository;

  @Override
  public Member find(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
  }
}

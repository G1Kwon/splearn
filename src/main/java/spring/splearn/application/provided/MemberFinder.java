package spring.splearn.application.provided;

import spring.splearn.domain.Member;

/**
 * 회원을 조회한다.
 */
public interface MemberFinder {

  Member find(Long memberId);

}

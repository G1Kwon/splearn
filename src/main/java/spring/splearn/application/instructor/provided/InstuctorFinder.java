package spring.splearn.application.instructor.provided;

import java.util.Optional;
import spring.splearn.domain.Instructor.Instructor;
import spring.splearn.domain.member.Member;

/**
 * 강사 조회
 */
public interface InstuctorFinder {

  Instructor find(Long instructorId);

  Optional<Instructor> findByMember(Long memberId);

  default Optional<Instructor> findMember(Member member) {
    return findByMember(member.getId());
  }

}

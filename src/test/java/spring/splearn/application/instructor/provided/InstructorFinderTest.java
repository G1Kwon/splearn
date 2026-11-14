package spring.splearn.application.instructor.provided;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.splearn.application.member.provided.MemberRegister;
import spring.splearn.application.support.stereotype.ApplicationServiceTest;
import spring.splearn.domain.Instructor.Instructor;
import spring.splearn.domain.member.Member;
import spring.splearn.domain.member.MemberFixture;

@ApplicationServiceTest
@RequiredArgsConstructor
class InstructorFinderTest {

  final InstructorFinder instructorFinder;
  final InstructorApplication instructorApplication;
  final MemberRegister memberRegister;

  @Test
  void findByMember() {
    Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
    member = memberRegister.activate(member.getId());

    Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

    Instructor found = instructorFinder.findByMember(member.getId()).orElseThrow();

    assertThat(instructor).isEqualTo(found);

    assertThat(instructorFinder.findByMember(Long.MAX_VALUE).isPresent()).isFalse();
  }

}
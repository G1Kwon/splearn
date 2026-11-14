package spring.splearn.application.instructor.provided;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.splearn.application.instructor.required.InstructorRepository;
import spring.splearn.application.member.required.MemberRepository;
import spring.splearn.application.support.stereotype.ApplicationServiceTest;
import spring.splearn.domain.Instructor.Instructor;
import spring.splearn.domain.Instructor.InstructorFixture;
import spring.splearn.domain.Instructor.InstructorStatus;
import spring.splearn.domain.member.Member;
import spring.splearn.domain.member.MemberFixture;

@ApplicationServiceTest
@RequiredArgsConstructor
class InstructorApplicationTest {

  final InstructorApplication instructorApplication;
  final InstructorRepository instructorRepository;
  final MemberRepository memberRepository;

  @Test
  void apply() {
    Member member = MemberFixture.createActiveMember();
    memberRepository.save(member);

    Instructor instructor = instructorApplication.apply(
        InstructorFixture.createApplyRequest(member));

    assertThat(instructor.getId()).isNotNull();
    assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);

    Instructor foundInstructor = instructorRepository.findById(instructor.getId()).get();

  }

  @Test
  void duplicateApply() {
    Member member = MemberFixture.createActiveMember();
    memberRepository.save(member);

    instructorApplication.apply(InstructorFixture.createApplyRequest(member));

    Assertions.assertThatThrownBy(
            () -> instructorApplication.apply(InstructorFixture.createApplyRequest(member)))
        .isInstanceOf(DuplicationInstructorApplicationException.class);
  }

  @Test
  void approve() {
    Instructor instructor = instructorApplication.approve(preparePendingInstructor().getId());

    assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);
  }

  @Test
  void reject() {
    Instructor instructor = instructorApplication.reject(preparePendingInstructor().getId());

    assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.REJECTED);
  }

  private Instructor preparePendingInstructor() {
    Member member = MemberFixture.createActiveMember();
    memberRepository.save(member);
    return instructorApplication.apply(InstructorFixture.createApplyRequest(member));

  }

}

package spring.splearn.domain.Instructor;

import spring.splearn.application.instructor.provided.InstructorApplyRequest;
import spring.splearn.domain.member.Member;
import spring.splearn.domain.member.MemberFixture;

public class InstructorFixture {

  public static Instructor createInstructor(Member member) {
    return Instructor.apply(member);
  }

  public static Instructor createInstructor() {
    return createInstructor(MemberFixture.createActiveMember());
  }

  public static Instructor createActiveInstructor() {
    Instructor instructor = createInstructor();
    instructor.approve();
    return instructor;
  }

  public static InstructorApplyRequest createApplyRequest(Member member) {
    return new InstructorApplyRequest(member.getId());
  }
}

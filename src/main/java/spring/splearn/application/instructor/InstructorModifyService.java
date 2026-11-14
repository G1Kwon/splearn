package spring.splearn.application.instructor;

import lombok.RequiredArgsConstructor;
import spring.splearn.application.instructor.provided.DuplicationInstructorApplicationException;
import spring.splearn.application.instructor.provided.InstructorApplication;
import spring.splearn.application.instructor.provided.InstructorApplyRequest;
import spring.splearn.application.instructor.provided.InstructorFinder;
import spring.splearn.application.instructor.required.InstructorRepository;
import spring.splearn.application.member.provided.MemberFinder;
import spring.splearn.domain.Instructor.Instructor;
import spring.splearn.domain.member.Member;
import spring.splearn.support.stereotype.VAlidatedApplicationService;

@VAlidatedApplicationService
@RequiredArgsConstructor
public class InstructorModifyService implements InstructorApplication {

  private final InstructorRepository instructorRepository;
  private final InstructorFinder instructorFinder;
  private final MemberFinder memberFinder;

  @Override
  public Instructor apply(InstructorApplyRequest applyRequest) {
    // member 를 찾아오고
    Member member = memberFinder.find(applyRequest.memberId());

    checkDuplicateApplication(member);

    // instructor 만들고
    Instructor instructor = Instructor.apply(member);

    // instructor 저잘
    return instructorRepository.save(instructor);
  }

  private void checkDuplicateApplication(Member member) {
    if (instructorRepository.findByMemberId(member.getId()).isPresent()) {
      throw new DuplicationInstructorApplicationException("회원은 중복해서 강사 신청을 할 수 없습니다.");
    }
  }

  @Override
  public Instructor approve(Long instructorId) {
    Instructor instructor = instructorFinder.find(instructorId);
    instructor.approve();
    return instructorRepository.save(instructor);
  }

  @Override
  public Instructor reject(Long instructorId) {
    Instructor instructor = instructorFinder.find(instructorId);
    instructor.reject();
    return instructorRepository.save(instructor);
  }
}

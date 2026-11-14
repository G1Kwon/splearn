package spring.splearn.application.instructor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import spring.splearn.application.instructor.provided.InstructorApplication;
import spring.splearn.application.instructor.provided.InstructorApplyRequest;
import spring.splearn.application.instructor.provided.InstructorFinder;
import spring.splearn.application.instructor.required.InstructorRepository;
import spring.splearn.application.member.provided.MemberFinder;
import spring.splearn.domain.Instructor.Instructor;
import spring.splearn.domain.member.Member;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class InstructorModifyService implements InstructorApplication {

  private final InstructorRepository instructorRepository;
  private final InstructorFinder instructorFinder;
  private final MemberFinder memberFinder;

  @Override
  public Instructor apply(InstructorApplyRequest applyRequest) {
    // member 를 찾아오고
    Member member = memberFinder.find(applyRequest.memberId());

    // instructor 만들고
    Instructor instructor = Instructor.apply(member);

    // instructor 저잘
    return instructorRepository.save(instructor);
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

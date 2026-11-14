package spring.splearn.application.instructor;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import spring.splearn.application.instructor.provided.InstructorFinder;
import spring.splearn.application.instructor.required.InstructorRepository;
import spring.splearn.domain.Instructor.Instructor;
import spring.splearn.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class InstructorQueryService implements InstructorFinder {

  private final InstructorRepository instructorRepository;

  @Override
  public Instructor find(Long instructorId) {
    return instructorRepository.findById(instructorId).orElseThrow(
        () -> new IllegalArgumentException("강사를 찾을 수 없습니다. ID: " + instructorId));
  }

  @Override
  public Optional<Instructor> findByMember(Long memberId) {
    return instructorRepository.findByMemberId(memberId);
  }
}

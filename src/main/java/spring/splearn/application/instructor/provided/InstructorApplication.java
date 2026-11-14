package spring.splearn.application.instructor.provided;

import jakarta.validation.Valid;
import spring.splearn.domain.Instructor.Instructor;

/**
 * 강사 신청
 */
public interface InstructorApplication {

  Instructor apply(@Valid InstructorApplyRequest request);

  Instructor approve(Long instructorId);

  Instructor reject(Long instructorId);
}

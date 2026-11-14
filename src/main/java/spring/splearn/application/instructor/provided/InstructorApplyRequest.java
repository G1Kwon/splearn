package spring.splearn.application.instructor.provided;

import jakarta.validation.constraints.NotNull;

public class InstructorApplyRequest {

  @NotNull
  Long memberId;
}

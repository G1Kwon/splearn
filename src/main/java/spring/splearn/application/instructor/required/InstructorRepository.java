package spring.splearn.application.instructor.required;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import spring.splearn.domain.Instructor.Instructor;

public interface InstructorRepository extends Repository<Instructor, Long> {

  Instructor save(Instructor instructor);

  Optional<Instructor> findById(Long memberId);

  Optional<Instructor> findByMemberId(Long memberId);
}

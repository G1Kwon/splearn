package spring.splearn.adapter;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.splearn.domain.member.DuplicateEmailException;
import spring.splearn.domain.member.DuplicateProfileException;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(Exception exception) {

    return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
  }


  @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
  public ProblemDetail emailExceptionHandler(DuplicateEmailException exception) {

    return getProblemDetail(HttpStatus.CONFLICT, exception);
  }

  // RFC9457에 따르면 409 Conflict 상태 코드를 반환하는 것이 적절.
  private static ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
        exception.getMessage());

    problemDetail.setProperty("timeStamp", LocalDateTime.now());
    problemDetail.setProperty("exception", exception.getClass().getSimpleName());
    return problemDetail;
  }
}

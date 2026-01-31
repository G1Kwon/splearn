package spring.splearn.adapter.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import spring.splearn.domain.shared.Email;

class DummyEmailSenderTest {

  @Test
  @StdIo
  void dummyEmailSender(StdOut out) {
    DummyEmailSender dummyEmailSender = new DummyEmailSender();
    dummyEmailSender.send(new Email("g1@splearn.app"), "subject", "body");

    assertThat(out.capturedLines()[0]).isEqualTo(
        "DummyEmailSender send email: Email[address=g1@splearn.app]");
  }

}
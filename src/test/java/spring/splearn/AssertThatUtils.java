package spring.splearn;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.function.Consumer;
import org.assertj.core.api.AssertProvider;
import org.springframework.test.json.JsonPathValueAssert;
import spring.splearn.domain.member.MemberRegisterRequest;

public class AssertThatUtils {

  public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
    return value -> assertThat(value).isNotNull();
  }

  public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(
      MemberRegisterRequest request) {
    return value -> assertThat(value).isEqualTo(request.email());
  }

}

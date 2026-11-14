package spring.splearn.adapter.webapi;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static spring.splearn.AssertThatUtils.equalsTo;
import static spring.splearn.AssertThatUtils.notNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import spring.splearn.adapter.webapi.dto.MemberRegisterResponse;
import spring.splearn.application.member.provided.MemberRegister;
import spring.splearn.application.member.provided.MemberRegisterRequest;
import spring.splearn.application.member.required.MemberRepository;
import spring.splearn.application.support.stereotype.WebApiAdapterTest;
import spring.splearn.domain.member.Member;
import spring.splearn.domain.member.MemberFixture;
import spring.splearn.domain.member.MemberStatus;

@WebApiAdapterTest
@RequiredArgsConstructor
class MemberApiTest {

  final MockMvcTester mvcTester;
  final ObjectMapper objectMapper;
  final MemberRepository memberRepository;
  final MemberRegister memberRegister;

  @Test
  void register() throws JsonProcessingException, UnsupportedEncodingException {
    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
    String requestJson = objectMapper.writeValueAsString(request);

    MvcTestResult result = mvcTester.post().uri("/api/members")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson).exchange();

    assertThat(result)
        .hasStatusOk()
        .bodyJson()
        .hasPathSatisfying("$.memberId", notNull())
        .hasPathSatisfying("$.email", equalsTo(request));

    MemberRegisterResponse response = objectMapper.readValue(
        result.getResponse().getContentAsString(), MemberRegisterResponse.class);
    Member member = memberRepository.findById(response.memberId()).orElseThrow();

    assertThat(member.getEmail().address()).isEqualTo(request.email());
    assertThat(member.getNickname()).isEqualTo(request.nickname());
    assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void duplicateEmail() throws JsonProcessingException {
    memberRegister.register(MemberFixture.createMemberRegisterRequest());

    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
    String requestJson = objectMapper.writeValueAsString(request);

    MvcTestResult result = mvcTester.post().uri("/api/members")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson).exchange();

    assertThat(result)
        .apply(MockMvcResultHandlers.print())
        .hasStatus(HttpStatus.CONFLICT);

  }
}

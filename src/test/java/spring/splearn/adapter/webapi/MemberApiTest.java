package spring.splearn.adapter.webapi;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import spring.splearn.application.member.provided.MemberRegister;
import spring.splearn.domain.member.Member;
import spring.splearn.domain.member.MemberFixture;
import spring.splearn.domain.member.MemberRegisterRequest;

@WebMvcTest(MemberApi.class)
@RequiredArgsConstructor
class MemberApiTest {

  final MockMvcTester mvcTester;
  final ObjectMapper objectMapper;

  @MockitoBean
  MemberRegister memberRegister;

  @Test
  void register() throws JsonProcessingException {

    Member member = MemberFixture.createMember(1L);
    when(memberRegister.register(any())).thenReturn(member);
    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
    String requestJson = objectMapper.writeValueAsString(request);

    assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$.memberId").asNumber().isEqualTo(1);

    verify(memberRegister).register(request);
  }

  @Test
  void registerFail() throws JsonProcessingException {

    MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest("invalid email");
    String requestJson = objectMapper.writeValueAsString(request);

    assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .hasStatus(HttpStatus.BAD_REQUEST);

  }

}
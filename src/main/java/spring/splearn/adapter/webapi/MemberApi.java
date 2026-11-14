package spring.splearn.adapter.webapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spring.splearn.adapter.webapi.dto.MemberRegisterResponse;
import spring.splearn.application.member.provided.MemberRegister;
import spring.splearn.application.member.provided.MemberRegisterRequest;
import spring.splearn.domain.member.Member;
import spring.splearn.support.stereotype.WebApiAdapter;

@WebApiAdapter
@RequiredArgsConstructor
public class MemberApi {

  private final MemberRegister memberRegister;

  // register api -> /members POST
  @PostMapping("/api/members")
  public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
    Member member = memberRegister.register(request);

    return MemberRegisterResponse.of(member);
  }
}

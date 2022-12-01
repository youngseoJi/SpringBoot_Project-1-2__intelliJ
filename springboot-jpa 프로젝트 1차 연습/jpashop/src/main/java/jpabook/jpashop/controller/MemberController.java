package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor //  final Or @NotNull 붙은 필드의 생성자 자동 생성 - 따로 생성할필요 X
public class MemberController {

    private final MemberService memberService;

    // 회원 등록 폼 조회
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); // 빈 맴버등록 폼 객체를 담아줌
        return "members/createMemberForm"; // 뷰 렌더링
    }

    // 회원 등록
    @PostMapping("/members/new")

        // @Valid: validation 기능을 사용함 / 현재 여기선 MemberForm에 적용한 @NotEmpty(message = "회원명은 필수 입니다.") 사용함
        // @Valid & BindingResult는 검증 시 오류가 발생할 경우 에러를 담아둠
        public String create(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        // 주소 생성 (폼 데이터 -> 정보 설정)
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        // 회원 생성 (폼 데이터로 -> 정보 설정)
        Member member = new Member();
        member.setName(form.getName()); 
        member.setAddress(address);

        // 회원 가입 (저장)
        memberService.join(member);
        return "redirect:/"; // 회원 가입시 -> Home 화면으로 이동
    }
}

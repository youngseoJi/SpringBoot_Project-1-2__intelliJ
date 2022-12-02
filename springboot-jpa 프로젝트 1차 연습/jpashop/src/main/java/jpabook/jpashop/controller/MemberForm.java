package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


// 회원 등록 폼 필요 값

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원명은 필수 입니다.") // 지정한 필수 값이 비어있으면 에러메세지 발송
    private String name; // 회원명

    private String city; // 도시(시)
    private String street; // 거리(~로,번지)
    private String Zipcode; // 우편번호


}

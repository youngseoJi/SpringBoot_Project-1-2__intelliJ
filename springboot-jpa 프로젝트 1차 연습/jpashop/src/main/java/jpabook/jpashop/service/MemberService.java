package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 회원 서비스
@Service
// @Transactional : jpa 모든 데이터나 로직의 변경을 @Transactional 안에서 변경되야함  / 디폴트: (readOnly = false)
@Transactional(readOnly = true)
public class MemberService {

    @Autowired // 스피링이 스프링빈에 등록되어있는 멤버리토지로리를 인젝션,주입해줌
    private MemberRepository memberRepository;

    // 기능 : 회원가입

    @Transactional // (readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member); // 즁복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 검증 : 중복회원 가입 X
    private void validateDuplicateMember(Member member) {
        // exception 에러
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (! findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    // 기능 : 회원 전체(목록) 조회
    public  List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}

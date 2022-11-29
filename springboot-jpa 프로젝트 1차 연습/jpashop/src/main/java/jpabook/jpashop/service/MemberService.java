package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 회원 서비스
/** @Transactional :트랜잭션, 영속성 컨텍스트
 * jpa 모든 데이터나 로직의 변경을 트렌젝션 안에서 변경되야함
 * 디폴트 값: (readOnly = false)
 * (readOnly = true) :데이터의 변경이 없는 읽기 전용 메서드에 사용 - get */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // final : 컴파일 시점에 memberRepository 를 설정하지 않는 오류를 체크할 수 있다.
    private final MemberRepository memberRepository;

    // @RequiredArgsConstructor : final이 있는 필드로 생성자를 생성.
    /* 생성자 인젝션(주입)   ( 스프링 필드 주입 대신에 생성자 주입을 사용하는 것이 좋음)
        public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/


    // 기능 : 회원가입
    @Transactional // (readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member); // 즁복회원 검증
        memberRepository.save(member);
        return member.getId(); // 가입완료한 회원 id를 출력
    }

    // 검증 : 중복회원 가입 X
    private void validateDuplicateMember(Member member) {
        // 저장소에서 해당 회원의 이름이 있으면 [] findMembers에 담아준다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // [] findMembers에 값이 있으면?  exception 에러
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 기능 : 회원 전체(목록) 조회
    public  List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 기능 : 회원 한 명 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}

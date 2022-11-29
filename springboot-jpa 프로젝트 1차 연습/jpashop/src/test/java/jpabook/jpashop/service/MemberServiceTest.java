package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class) //  스프링과 테스트 통합
@SpringBootTest // 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
@Transactional // 반복 가능한 테스트 지원,각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백
               // ( 테스트 케이스에서 사용될 때만 롤백)DB 데이터 저장 X, 다음 테스트에 영향 주지 X

// 서비스 기능 테스트
public class MemberServiceTest {
    // 필드 주입방식
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(value = false) : 롤백X, DB 저장
    public void 회원가입() throws Exception {

        //given : 회원이 주어졌을때
        Member member = new Member();
        member.setName("Ji");

        //when : Ji가 회원가입 했을때, 회원가입한 회원이 id가 리턴되었을때
        Long saveId = memberService.join(member);

        //then : test - 가입한 회원 Ji와 저장소에 회원 id로 조회한 회원 이름이 같을 경우 OK
        em.flush();
        //  DB에 쿼리를 강제로 날림, insert 데이터 넣는 방법 - 롤백시 데이터는 지워짐
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class) // expected : 어느 예외가 터진다고 예상하고 선언해 주는 방식 - IllegalStateException타짐
    public void 중복_회원_예외() throws Exception {

        //given : 동일한 회원이 있을때
        Member member1 = new Member();
        member1.setName("ys");

        Member member2= new Member();
        member2.setName("ys");

        //when : 동일한 회원이 가입을 두번 요청했을때
        memberService.join(member1);
        memberService.join(member2); // 예외발생해야함!


        /*
        @Test(expected = IllegalStateException.class)
        ==
        try {
            memberService.join(member2); // 예외발생해야함
        } catch (IllegalStateException e) {
            return;
        }
        */

        //then : 예외발생하는 코드 밑으로 내려오면 실패.
        fail();
    }

}
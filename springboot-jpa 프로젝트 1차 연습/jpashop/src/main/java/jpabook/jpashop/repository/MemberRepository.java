package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext // 영속성 컨텍스트 - 엔티티를 영구저장
    private EntityManager em;
    //EntityManager.persist(entity);  : 엔티티를 컨텍스트에 저장
    
    // 회원 가입, 등록
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //.createQuery : 엔티티 객체 대상으로 쿼리  (차이점 : sql은 테이블을 대상으로 쿼리)

    // 회원 목록
    public List<Member> findAll() {
     //  멤버 엔티티 객체에서 멤버를 조회해라
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 회원 검색 기능 - 이름으로 검색 
    public List<Member> findByName(String name) { // 파라미터로 이름 던짐

        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}

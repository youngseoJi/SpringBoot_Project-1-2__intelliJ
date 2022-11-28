package jpabook.jpashop.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 회원 엔티티 클래스 개발
@Entity
@Getter
@Setter
/* 설명을 쉽게하기 위해 엔티티 클래스에 Getter, Setter 모두 열고, 단순하게 설계한 것
 실무에서는 가급적 Getter는 열어두고, Setter는 꼭 필요한 경우에만 사용하는 것을 추천 */
public class Member {

    @Id @GeneratedValue
    // PK 컬럼명은 member_id 사용(지정) -> 컬럼명 지정안하면 식별자 id로 컬럼명이 자동지정된다.
    @Column(name = "member_id")

    private Long id; // 엔티티 식별자 (member_id)

    private String name; // 회원명

    @Embedded // 내장타입을 포함했음 표시
    private Address address; // 주소

    // 화원과 주문 = 1:N 일대다 관계
    /* readonly(거울) member에서는 값변경 X ->  Order 주문에 FK 외래키가 있는것
       mappedBy="member" -> Order 테이블에 있는 member필드에 의해 맵핑된 것.*/
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 주문내역
}

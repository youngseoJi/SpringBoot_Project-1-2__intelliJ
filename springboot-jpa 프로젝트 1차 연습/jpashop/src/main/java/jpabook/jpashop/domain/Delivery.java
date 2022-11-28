package jpabook.jpashop.domain;

// 배달 엔ㅌ티티

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // 배달과 주문 = 1:1 관계
    /* readonly(거울) delivery에서는 값변경 X ->  Order 주문에 FK 외래키가 있음
       mappedBy="delivery" -> Order 테이블에 있는 order필드에 의해 맵핑된 것*/
    @OneToOne(mappedBy = "delivery")
    private Order order; // 주문

    @Embedded // 내장타입
    private Address address; // 배송지 주소

    // EnumType.STRING 으로 사용해야한다. 중간에 새로운 값이 들어와도 OK ( 디폴트 ORDINAL 사용 X : 컬럼이 숫자로 들어감, 중간에 새로운 값이 들어오면 순서 밀려버림 에러 )
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // 배송상태 [READY, COMP]
}

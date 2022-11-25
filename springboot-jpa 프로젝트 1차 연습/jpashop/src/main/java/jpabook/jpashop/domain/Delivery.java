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

    private Order order; // 주문

    @Embedded // 내장타입
    private Address address; // 배송지 주소

    private DeliveryStatus status; // 배송상태 [READY, COMP]
}

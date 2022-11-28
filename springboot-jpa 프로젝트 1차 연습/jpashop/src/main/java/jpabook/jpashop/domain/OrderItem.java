package jpabook.jpashop.domain;

// 주문한 아이템 - 중간 테이블

import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.damain.item.Item;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id; 

    @ManyToOne // 주문상품과 상품 = N:1 관계
    @JoinColumn(name = "item_id") // FK 외래키 orderItem에 있음.
    private Item item;

    @ManyToOne // 주문상품과 주문 = N:1 관계 -> 1개의 주문에 여러개의 item을 갖음, 주문할 상품은 1개의 주문과 매핑
    @JoinColumn(name = "order_id") // FK 외래키 orderItem에 있음.
    private Order order; // 주문

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

}

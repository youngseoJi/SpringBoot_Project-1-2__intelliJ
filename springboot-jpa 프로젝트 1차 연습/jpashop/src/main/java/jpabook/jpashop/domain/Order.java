package jpabook.jpashop.domain;

// 주문 엔티티

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    
    @ManyToOne  // 주문과 회원 = N:1 다대일 관계 ( 실전에서는 사용 X 에노테이션)
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    /* readonly(거울) order에서는 값변경 X ->  OrderItem 주문상품에 FK 외래키가 있는것
      mappedBy="order" -> OrderItem 테이블에 있는 order필드에 의해 맵핑된 것.*/
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문한 아이템들

    // 주문과 배달 = 1:1 관계 -> 1:1관계일 경우에는 연관관계의 주인 pk키를 중심적인 곳에 둔다. 주문에 pk키 두기
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태 -> [order, cancel]
}

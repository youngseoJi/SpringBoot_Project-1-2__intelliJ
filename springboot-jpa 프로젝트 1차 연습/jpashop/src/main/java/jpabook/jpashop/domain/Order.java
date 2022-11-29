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
    
    @ManyToOne(fetch = FetchType.LAZY)  // 주문과 회원 = N:1 다대일 관계 ( 실전에서는 사용 X 에노테이션)
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    /* readonly(거울) order에서는 값변경 X ->  OrderItem 주문상품에 FK 외래키가 있는것
      mappedBy="order" -> OrderItem 테이블에 있는 order필드에 의해 맵핑된 것.*/
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문한 아이템들

    // 주문과 배달 = 1:1 관계 -> 1:1관계일 경우에는 연관관계의 주인 pk키를 중심적인 곳에 둔다. 주문에 pk키 두기
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)

    private OrderStatus status; // 주문상태 -> [order, cancel]

    // 연관관계 메서드
    //  양방향 관계일 경우사용, 메서드가 있는 곳은 실제 컨트롤하는 곳에 존재한다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    // 주문 생성 메서드- 복잡한것은 따로 뺴서 만들어준다.
    // Order 가 order 주문 연관관계를 묶으면서, 주문 생성
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems ) {
                                                                // 가변인자 : 여러개의 매개변수를 받음 OrderItem... orderItems
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 주문상태
        order.setOrderDate(LocalDateTime.now()); // 주문시간
        return order; // 주문 셋팅
    }

    // 비즈니스 로직 //

    // 주문취소
    public  void cancel() {
        // 배송상태가 == COMP 배송완료일때 -> 에러
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 주문 취소가 불가능합니다.");
        }
        // 아닐경우? 주문 상태를 취소로 변경한다.
        this.setStatus(OrderStatus.CANCEL);

        // 주문 상품 각각에 취소요청
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회 로직 //

    // 전체 주문가격 조회
   public int getTotalPrice() {
        int totalPrice = 0;
        // 각 item의 총 주문 가격을 모두 더해주기
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
/*  동일한 로직 - stream 활용
    public int getTotalPrice() {
      return orderItems.stream()
              .mapToInt(OrderItem::getTotalPrice)
              .sum();
    }
 */

}





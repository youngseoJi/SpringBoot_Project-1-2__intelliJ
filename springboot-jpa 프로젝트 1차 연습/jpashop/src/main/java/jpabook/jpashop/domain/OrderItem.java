package jpabook.jpashop.domain;

// 주문한 아이템 - 중간 테이블

import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.domain.item.Item;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id; 

    @ManyToOne(fetch = FetchType.LAZY) // 주문상품과 상품 = N:1 관계
    @JoinColumn(name = "item_id") // FK 외래키 orderItem에 있음.
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) // 주문상품과 주문 = N:1 관계 -> 1개의 주문에 여러개의 item을 갖음, 주문할 상품은 1개의 주문과 매핑
    @JoinColumn(name = "order_id") // FK 외래키 orderItem에 있음.
    private Order order; // 주문

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량


    // 주문상품 - 생성메서드 //
    public  static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문상품
        orderItem.setOrderPrice(orderPrice); // 가격
        orderItem.setCount(count); // 주문 개수

        item.removeStock(count); // 재고에서 주문한 개수만큼 빼기
        return orderItem;
    }



    // 비즈니스 로직 //

    // 주문 상품 취소
    public  void cancel() {
        // 재고수량 복구 (취소한 개수만큼)
        getItem().addStock(count);
    }

    // 조회 로직 //

    // 주문한 item 총 가격 조회
    public int getTotalPrice() {
        // 주문 item * 개수
        return getOrderPrice() * getCount();
    }
}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 주문 서비스
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문 : 회원정보에 있는 주소지를 배송 주소지로 설정
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); // 회원정보에 있는 주소지를 배송 주소지로 설정

        // 주문상품 생성 - 생성메서드 활용
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); // 상품, 상품금액, 상품 구매 개수

        // 주문 생성 - 생성메서드 활용
        Order order = Order.createOrder(member, delivery, orderItem); /// 회원정보, 배달정보, 주문하는 상품 정보

        // 주문 저장 
        orderRepository.save(order); // 주문 저장소에 주문을 저장

        return order.getId();

    }


    
    // 주문 취소 : 주문 식별자를 받아서 주문 엔티티를 조회한 후 주문 엔티티에 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 조회 (주문 id로 조회)
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    // 검색 : OrderSearch 라는 검색 조건을 가진 객체로 주문 엔티티를 검색
}

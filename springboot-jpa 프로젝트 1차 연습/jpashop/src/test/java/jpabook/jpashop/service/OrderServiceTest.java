package jpabook.jpashop.service;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given : 회원 정보, 상품(책)정보
        Member member = new Member();
        member.setName("ys");
        member.setAddress(new Address("서울","길","123-456"));
        em.persist(member);

        Book book = new Book();
        book.setName("JPA 초보학습");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        //when : 주문할 때
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then : TEST 주문시 주문 상태, 주문한 상픔 종류수 , 총 주문 가격, 재고수량이 기대하는 값과 실제값이 같은지

        Order getOrder = orderRepository.findOne(orderId);

        // assertEquals (메세지, 기대하는 값, 실제값)
        assertEquals("상품주문시 주문상태 ORDER", OrderStatus.ORDER, getOrder.getStatus()); // 주문 상태
        assertEquals("주문한 상품 종류 수가 정확해야함", 1, getOrder.getOrderItems().size()); // 주문 개수
        assertEquals("주문 가격은 가격 * 수량임", 10000 * orderCount, getOrder.getTotalPrice()); // 주문가격
        assertEquals("주문 수량만큼 재고가 줄어야함", 8, book.getStockQuantity()); // 재고수량

    }
    
    @Test
    public void 주문취소() throws Exception {
        //given
        
        //when
        
        //then
        
    }
    
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        
        //when
        
        //then
        
    }
}

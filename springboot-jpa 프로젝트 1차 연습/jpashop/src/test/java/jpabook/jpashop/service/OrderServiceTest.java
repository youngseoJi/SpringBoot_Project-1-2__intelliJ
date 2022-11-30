package jpabook.jpashop.service;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
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
        //given : 회원 정보, 상품(책)정보, 주문 개수

        Member member = createMember();
        Book book = createBook("JPA 학습", 10000, 10);

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

    // 예외 테스트 중요하다! - 상품주문할때, 재고보다 많이 시킬때 에러발생

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given : 재고 10개를 넘어선 주문량이 들어오면
        Member member = createMember();
        Item item = createBook("JPA 학습", 10000, 10); 

        int orderCount = 11;
        
        //when : 주문했을 때 -> exeption 에러 발생
        orderService.order(member.getId(), item.getId(),orderCount);

        //then : 밑에 코드까지 내려오면 안됨
        fail("재고 수량 부족 예외가 발생해야 한다!!");
    }
    @Test
    public void 주문취소() throws Exception {
        //given

        //when

        //then

    }

    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("ys");
        member.setAddress(new Address("서울","길","123-456"));
        em.persist(member);
        return member;
    }
}

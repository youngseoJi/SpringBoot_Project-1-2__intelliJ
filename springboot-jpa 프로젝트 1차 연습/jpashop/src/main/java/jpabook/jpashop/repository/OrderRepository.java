package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

// 주문 리포지토리
@Repository
@RequiredArgsConstructor // final 붙은필드 자동 생성자 생성
public class OrderRepository {

    private final EntityManager em;

    // 주문 내역 저장
    public void save(Order order) {
        em.persist(order);
    }

    // 주문 한 건 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }
    
    // 주문 검색
//    public List<Order> findAll(OrderSerch orderSearch){}
}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    /* 변경 감지와 병합(merge)
    *
    * 준영속 엔티티를 수정하는 2가지 방법 - 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다
    * 변경 감지 기능 사용
    * 병합( merge ) 사용
     */
     @Test
    public void updateTest() throws Exception {

        Book book = em.find(Book.class, 1L);

        //tx
        book.setName("111");

        // 변경 감지 == dirty checkig
        // tx commit
        
    }
}

package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


// 상품 리포지토리
@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 상품등록
    public void save(Item item) {
        // item이 없다는 것은 새로 생성한 객체라는 뜻
        if (item.getId() == null) {
            // item 신규 등록
            em.persist(item);

            // 수정할 목적!!! - item 이 이미 있다, db에서 가져온것
        } else {
            // 준영속 엔티티를 수정하는 2가지 방법 중 1개 - 병합(merge) 사용
            // 저장, 업데이트 같은 기능 -> 실무에서 사용 X
            em.merge(item);
        }
    }

    // 상품 조회
    public Item findOne(Long id) {
        return em.find(Item.class,id);
    }

    // 모든 상품 조회
    public List<Item> findAll() {
        // 엔티티 객체인 Item 의 모든 데이터를 List [] 로 추출
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}

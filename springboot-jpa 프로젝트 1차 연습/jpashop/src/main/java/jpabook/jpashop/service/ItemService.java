package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 상품 서비스 - 상품 리파지토리에 위임만 하는 클래스
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 생성자 주입 final 또는 @NotNull 붙은 필드의 생성자를 자동 생성
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // (readOnly = true)면 저장  X
    // item 저장
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 준영속 엔티티를 수정하는 2가지 방법 중 -변경 감지 기능 사용
    // merge 병합 사용 X - 엔티티를 변경할 때는 항상 변경 감지를 사용해라!
    @Transactional
    public Item updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId);

        findItem.setName(param.getName());
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        return findItem;
    }

    // item 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 모든 item 목록 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}

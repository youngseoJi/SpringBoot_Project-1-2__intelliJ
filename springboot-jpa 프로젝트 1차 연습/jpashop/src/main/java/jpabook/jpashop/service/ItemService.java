package jpabook.jpashop.service;

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
    // item 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 모든 item 목록 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}

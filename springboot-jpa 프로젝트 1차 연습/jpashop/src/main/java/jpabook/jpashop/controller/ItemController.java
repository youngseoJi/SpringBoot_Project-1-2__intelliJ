package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 상품 컨트롤러
@Controller
@RequiredArgsConstructor // 생성자 자동 생성
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    // 상품 등록폼
    @GetMapping("/new")
    public String createForm(Model model) {

        model.addAttribute("form", new BookForm()); // BookForm 등록폼, 빈 객체 담기
        return "items/createItemForm"; // 상품 등록폼
    }

    // 상품 등록
    @PostMapping("/new")
    public String create(BookForm form) {

        // 책 객체 생성
        Book book = new Book();

        // 책 설정 : form에 입력된 폼데이터 담아주기
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        // 상품저장소에 생성한 책을 저장
        itemService.saveItem(book);

        // 저장 후 리다이렉트 - 홈
        return "redirect:/items";

    }

    // 상품목록 조회
    @GetMapping()
    public String list(Model model) {

        // 상품들을 저장소에서 꺼내 list[]인 items에 담음 -> model 에 저장
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        
        return "items/itemList";  // itemList 상품목록 뷰 렌더링
    }

    // 상품 수정 폼 조회
    
    // @PathVariable {변수/바뀌는 값} 고정되지 않은 값이 있을때 사용
    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        // 수정하는 상품 Id로 조회 -> Book 책으로 타입변환
        Book item = (Book) itemService.findOne(itemId);

        // 책/상품 등록 폼 생성
        BookForm form = new BookForm();

        // 책/상품 등록 폼에 작성한 데이터 셋팅, 담기
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        // form 에 담긴 데이터 model에 저장하기
        model.addAttribute("form", form);

        return "items/updateItemForm"; // item 수정 폼 뷰 렌더링
    }

    // 상품 수정
    @PostMapping("/{itemId}/edit")

    // @ModelAttribute("form") : updateItemForm 뷰 파일에서 -> <form th:object="${form}" ~> 해당 form 을 갖고옴
    public String updateItem(@ModelAttribute("form") BookForm form) {
        // 책 객체 생성 - 초기화
        Book book = new Book();

        // 책 정보 설정 - 수정 폼에 입력한 데이터로 데이터 바인딩
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        // 수정한 책 정보로 다시 저장
        itemService.saveItem(book);

        // 수정 후 -> 상품목록 뷰 리다이렉트
        return "redirect:/items";
    }
}

package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// 상품 컨트롤러
@Controller
@RequiredArgsConstructor // 생성자 자동 생성
//@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    // 상품 등록폼
    @GetMapping("/items/new")
    public String createForm(Model model) {

        model.addAttribute("form", new BookForm()); // BookForm 등록폼, 빈 객체 담기
        return "items/createItemForm"; // 상품 등록폼
    }

    // 상품 등록
    @PostMapping("/items/new")
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

        // 저장 후 리다이렉트 -> 상품 목록 뷰
        return "redirect:/items";

    }
}

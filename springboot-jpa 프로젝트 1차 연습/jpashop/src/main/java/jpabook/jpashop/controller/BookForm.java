package jpabook.jpashop.controller;


import lombok.Getter;
import lombok.Setter;

// item 상품 등록 폼
@Getter @Setter
public class BookForm {

    // item 상품 공통 속성
    private Long id; // 상품 번호 id

    private String name; // 제목
    private int price; // 가격
    private int stockQuantity; // 재고

    // item 중 책 특수 속성
    private String author; // 저자
    private String isbn; //국제표준도서번호
}

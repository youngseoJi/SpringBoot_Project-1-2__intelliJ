package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

// Item 상속관계 매핑
@Entity
// 구분할 수 있는 값 지정 -> 싱글테이블이여서 저장할때 DB가 구분하는 값
// Item 상속관계 매핑
@Getter @Setter
public class Book extends Item{

    private String author; // 저자

    private String isbn; //국제표준도서번호
}

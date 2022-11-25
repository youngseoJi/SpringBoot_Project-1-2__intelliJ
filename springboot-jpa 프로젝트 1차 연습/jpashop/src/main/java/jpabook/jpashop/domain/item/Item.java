package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 상품 엔티티 - 추상클래스 abstract
@Entity
@Getter @Setter
public abstract class Item {

    // 상속관계 매핑 : Item -> Book, Album, Movie

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id; // 식별자 pk, 칼럼명 item_id 설정

    private String name;

    private int price;

    private int stock;

    private int stockQuantity;
}

package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 카테고리 엔티티
@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 실무에서 쓰지 않는방법 - 중간 테이블( CATEGORY_ITEM )에 컬럼을 추가 할 수 없고, 세밀하게 쿼리를 실행하기 어렵다는 한계가 있음
    @ManyToMany // 카테고리와 상품 = N:N 관계
    @JoinTable(name = "category_item",  // 중간 테이블 매핑
            joinColumns = @JoinColumn(name = "category_id"), // 중간 테이블에 있는 식별자
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블의 item 식별자

    private List<Item> items = new ArrayList<>();

    // 카테고리 계층구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //연관관계 메서드 - 양방향 관계
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}

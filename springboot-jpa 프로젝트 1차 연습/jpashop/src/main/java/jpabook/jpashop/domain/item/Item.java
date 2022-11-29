package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// 상품 엔티티 - 추상클래스 abstract
@Entity
// 상속관계 매핑 -> 부모에게 전략타입 설정해야한다. : InheritanceType.SINGLE_TABLE 싱글테이블 타입 지정
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dType")
@Getter @Setter
public abstract class Item {

    // 상속관계 매핑 : Item -> Book, Album, Movie

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id; // 식별자 pk, 칼럼명 order_item_id 설정

    private String name;
    private int price;
    private int stockQuantity; // 재고 개수

    // N:N 관계
     /* readonly(거울) item 에서는 값변경 X ->  categories 카테고리에 FK 외래키가 있음
       mappedBy="items" -> Categorey 테이블에 있는 categories필드에 의해 맵핑된 것*/
    @ManyToMany(mappedBy = "items")
    public List<Category> categories = new ArrayList<>();


    // 비즈니스 로직 //

    // 재고 증가
    public  void  addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고 감소
     public void removeStock(int quantity) {
          int restStock = this.stockQuantity - quantity;

          if (restStock < 0) {
              throw new NotEnoughStockException("재고가 0보다 적으면 안된다.");
          }
          this.stockQuantity = restStock;
     }
}

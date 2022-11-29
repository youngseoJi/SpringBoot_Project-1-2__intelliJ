package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// Item 상속관계 매핑
@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {

    private String director; // 감독

    private String actor; // 배우
}

package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// Item 상속관계 매핑
@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item{

    private String artist;

    private  String etc;
}

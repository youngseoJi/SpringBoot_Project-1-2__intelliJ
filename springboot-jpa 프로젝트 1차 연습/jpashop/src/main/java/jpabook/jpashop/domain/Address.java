package jpabook.jpashop.damain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

// 주소 값 타입
@Embeddable // jpa 내장타입, 어딘가에 내장될수있음
@Getter
// @Setter 제거 -> 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스 생성
/* JPA 스펙상 엔티티나 임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는
   protected 로 설정해야 한다. public 으로 두는 것 보다는 protected 로 설정하는 것이 그나마 더 안전하다.
 */
public class Address {
    private  String city;

    private String street;

    private String zipcode;

}

package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @JsonIgnore // 조회할 때, JsonIgnore Annotation을 사용하면 배제하고 조회 가능.
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")  // 일대다 관계, order 테이블에 있는 member 필드에 의해 mapping
    private List<Order> orders = new ArrayList<>();


}

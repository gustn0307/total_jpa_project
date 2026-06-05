package com.my.total_jpa_back.orders.entity;

import com.my.total_jpa_back.common.entity.BaseEntity;
import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user_order")
public class UserOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "user_id") // Users 테이블의 외래키
//    private Long userId;

    // Users 객체 자체를 포함
    // EAGER타입으로 fetch하면 실행할 때 left join으로 맵핑된 테이블 정보까지 한 번에 가져옴
    // ddl-auto를 create로 설정 후 아래처럼 맵핑해주면 자동으로 FK 설정도 해줌
    @ManyToOne(fetch = FetchType.LAZY) // default : EAGER, 왠만하면 그냥 LAZY 사용하면 됨
    @JoinColumn(name = "user_id") // user_id 컬럼을 Users의 @Id가 붙은 PK와 연결(FK)
    private Users user;

    @Column(name = "product_name")
    private String productName;

    private Integer price; // 나중에 QueryDSL 조건 때문에 Integer로 선언

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}

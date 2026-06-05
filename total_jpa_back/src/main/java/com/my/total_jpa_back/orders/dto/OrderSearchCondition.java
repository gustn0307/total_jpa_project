package com.my.total_jpa_back.orders.dto;

import com.my.total_jpa_back.common.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

// QueryDSL 에서 사용할 조건에 대한 리스트의 DTO
@Getter
@Setter
public class OrderSearchCondition {
    // 주문 상태로 검색용 변수
    private OrderStatus status;

    // 최저가격 검색용 변수
    // 래퍼 클래스로 사용하는 이유: 널 처리 때문
    private Integer minPrice;

    // 고객 이름 검색용 변수
    private String userName;

    // 최대가격 검색용 변수
    private Integer maxPrice;
}

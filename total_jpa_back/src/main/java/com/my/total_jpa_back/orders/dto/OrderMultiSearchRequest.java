package com.my.total_jpa_back.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.my.total_jpa_back.common.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMultiSearchRequest {
    @JsonProperty("json_status") // Request의 Body의 변수와 자바의 변수를 맵핑
    private OrderStatus status;

    @JsonProperty("json_price")
    private Integer price;

    @JsonProperty("json_keyword")
    private String keyword;
}

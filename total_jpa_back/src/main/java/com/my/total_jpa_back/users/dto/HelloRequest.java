package com.my.total_jpa_back.users.dto;

import lombok.*;

@Getter
@Setter
public class HelloRequest {
    // API 요청시 전달되는 매개변수들을 받는 DTO
    // POST Mapping 요청 용도로 사용
    private String name;
    private int age;
}

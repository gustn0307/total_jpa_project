package com.my.total_jpa_back.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HelloResponse {
    //  API 응답 결과를 담기 위한 DTO

    private String message;
    private int age;

}

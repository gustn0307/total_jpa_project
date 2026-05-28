package com.my.total_jpa_back.users.dto;

import com.my.total_jpa_back.common.entity.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest { // 프론트에서 저장을 요청한 정보가 있는 DTO
    private String name;
    private Gender gender;
    private String email;
    private String likeColor;
}

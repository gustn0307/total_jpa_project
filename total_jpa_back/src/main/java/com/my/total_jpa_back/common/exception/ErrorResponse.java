package com.my.total_jpa_back.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse { // 오류 처리 결과를 정리해서 보낼 DTO

    private int status; // 에러 상태 코드
    private String message; // 오류 메시지
}

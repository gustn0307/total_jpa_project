package com.my.total_jpa_back.common.dto;


import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {

    private List<T> content; // 검색한 결과를 담을 DTO의 리스트 => content

    private int page; // 현재 페이지

    private int size; // 페이지 당 리스트 수

    private Long totalElements;  // 전체 리스트의 수

    private int totalPages; // 전체 페이지 수

    private boolean hasNext; // 다음 페이지 존재 여부

    // 생성자 (페이지를 통으로 넘겨받아서 DTO로 변환)
    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
    }
}

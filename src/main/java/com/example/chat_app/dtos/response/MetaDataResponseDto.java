package com.example.chat_app.dtos.response;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MetaDataResponseDto<T> {
    private Integer totalPages;
    private Long totalElements;

    private boolean hasNext;
    private boolean hasPrevious;

    private Integer pageNumber;
    private Integer pageSize;

    public MetaDataResponseDto(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
        this.pageNumber = page.getPageable().getPageNumber() + 1;
        this.pageSize = page.getPageable().getPageSize();
    }
}

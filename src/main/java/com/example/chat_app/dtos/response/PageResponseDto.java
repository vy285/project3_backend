package com.example.chat_app.dtos.response;

import java.util.List;

import com.example.chat_app.dtos.response.MetaDataResponseDto;
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
public class PageResponseDto<T> {
    private Integer code;
    private List<T> data;
    private MetaDataResponseDto<T> metadata;

    public PageResponseDto(Integer code, Page<T> page) {
        this.code = code;
        this.data = page.getContent();
        this.metadata = new MetaDataResponseDto<T>(page);
    }
}
package com.example.chat_app.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDto<T> {
    public String code;
    public T data;
}

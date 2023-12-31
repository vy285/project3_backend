package com.example.chat_app.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDto<T> {
    public Integer code;
    public T data;
}

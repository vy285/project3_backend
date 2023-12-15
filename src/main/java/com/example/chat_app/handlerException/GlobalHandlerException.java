package com.example.chat_app.handlerException;

import com.example.chat_app.dtos.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleResponseStatusException(ResponseStatusException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getStatusCode().toString());
        errors.put("message", e.getReason());
        return ResponseEntity.status(400).body(new ResponseDto<>(400, errors));
    }
}

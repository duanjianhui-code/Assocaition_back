package com.assocaition.management.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseResult<T> {
    private int code=20000;
    private String status="success";
    private T data;

    @Data
    @AllArgsConstructor
    public class Token{
        private String token;
    }
}

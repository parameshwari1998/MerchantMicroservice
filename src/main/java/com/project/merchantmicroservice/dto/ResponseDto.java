package com.project.merchantmicroservice.dto;

import lombok.Data;

@Data
public class ResponseDto<T> {

    private Boolean success;
    private String message;
    private T data;

}

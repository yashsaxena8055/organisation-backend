package com.example.org.template.excel.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseWO<T> {
    private String message;
    private T data;
    private String status;

}

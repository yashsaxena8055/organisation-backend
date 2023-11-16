package com.example.org.template.excel.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExcelResponse {
    private List<String> header;
    private List<Map<String, String>> list;
}

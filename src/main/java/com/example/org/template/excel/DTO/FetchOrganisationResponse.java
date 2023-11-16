package com.example.org.template.excel.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class FetchOrganisationResponse  {
    private String orgName;
    private Map<String,String> columnMapping;
    public FetchOrganisationResponse(String orgName,Map<String,String> columnMapping){
        this.orgName = orgName;
        this.columnMapping = columnMapping;
    }
}

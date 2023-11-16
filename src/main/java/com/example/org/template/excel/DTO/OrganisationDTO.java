package com.example.org.template.excel.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrganisationDTO {
    private String organisationName;
    private List<String> columns;
}

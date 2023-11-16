package com.example.org.template.excel.service;

import com.example.org.template.excel.DAO.OrganisationDAO;
import com.example.org.template.excel.DTO.ExcelResponse;
import com.example.org.template.excel.model.MetaData;
import com.example.org.template.excel.model.Organisation;
import com.example.org.template.excel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class TemplateService {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private OrganisationDAO organisationDAO;
    @Autowired
    private MetaDataService metaDataService;

    public ExcelResponse uploadTemplate(MultipartFile file, String orgName) throws Exception {
        if (!StringUtils.hasText(orgName)) {
            throw new Exception("Organisation name is not present");
        }
        if (Objects.isNull(file) || file.isEmpty()) {
            throw new Exception("Please upload the file");
        }
        Optional<Organisation> optional = organisationDAO.findByName(orgName);
        if (!optional.isPresent()) {
            throw new Exception("Invalid organisation Name");
        }
        List<String> columns = Arrays.asList(optional.get().getSystemCol().split("\\^"));
        List<Map<String, String>> data = excelService.parseExcel(file);
        metaDataService.createMetadata(data, optional.get());
        ExcelResponse response = new ExcelResponse();
        response.setHeader(columns);
        response.setList(data);
        return response; 
       
    }
}

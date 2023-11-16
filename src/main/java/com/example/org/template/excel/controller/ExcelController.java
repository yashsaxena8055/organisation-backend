package com.example.org.template.excel.controller;

import com.example.org.template.excel.DAO.OrganisationDAO;
import com.example.org.template.excel.DTO.ExcelResponse;
import com.example.org.template.excel.model.Organisation;
import com.example.org.template.excel.service.ExcelService;
import com.example.org.template.excel.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/temp")
public class ExcelController {

@Autowired
private TemplateService templateService;
@Autowired
private OrganisationDAO organisationDAO;
@Autowired
private ExcelService excelService;
@GetMapping("/download")
public ResponseEntity download(@RequestParam("orgName") String orgName, HttpServletResponse response) throws IOException {
    Optional<Organisation> optional = organisationDAO.findByName(orgName);
    if(!optional.isPresent()){
        return ResponseEntity.badRequest().body("No organisation present with this name");
    }
    excelService.downloadTemplate(response,optional.get());
    return ResponseEntity.ok("Excel generated successfully");
}
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestPart MultipartFile file, @RequestParam("orgName") String orgName) throws Exception {
        ExcelResponse response = templateService.uploadTemplate(file,orgName);
//        if(!optional.isPresent()){
//            return ResponseEntity.badRequest().body("No organisation present with this name");
//        }
//        if(file.isEmpty()){
//            return ResponseEntity.badRequest().body("file not present");
//        }
//        List<String> column = Arrays.asList(optional.get().getTemplateCol().split("\\^"));
//        excelService.parseExcel(file,column);
        return ResponseEntity.ok(response);
    }
}

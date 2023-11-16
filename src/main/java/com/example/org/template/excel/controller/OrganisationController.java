package com.example.org.template.excel.controller;

import com.example.org.template.excel.DTO.FetchOrganisationResponse;
import com.example.org.template.excel.DTO.OrganisationDTO;
import com.example.org.template.excel.DTO.ResponseWO;
import com.example.org.template.excel.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/main")
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseWO> createOrganisation(@RequestBody OrganisationDTO request) throws Exception {
        organisationService.createOrganisation(request);
        ResponseWO responseWO = ResponseWO.builder().data(null).message("Organisation Created successfully").status("OK").build();
        return ResponseEntity.ok(responseWO);
    }
    
    @GetMapping("/fetchAll")
    public ResponseEntity fetchEntity()throws Exception{
        List<FetchOrganisationResponse> responseList = organisationService.fetchOrg();
        return ResponseEntity.ok(responseList);
    }
    @GetMapping("/fetchByName") 
    public ResponseEntity fetchByName(@RequestParam("orgName") String orgName)throws Exception{
        List<FetchOrganisationResponse> responseList = organisationService.fetchOrg();
        FetchOrganisationResponse response = new FetchOrganisationResponse(null,null);
        if(!responseList.isEmpty()){
            Optional<FetchOrganisationResponse>  opt = responseList.stream().filter(list -> list.getOrgName().equals(orgName)).findAny();
            if(opt.isPresent())
                response = opt.get();
        }
        return ResponseEntity.ok(response);
    }
    
    
}

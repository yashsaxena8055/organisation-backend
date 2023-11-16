package com.example.org.template.excel.service;

import com.example.org.template.excel.DAO.OrganisationDAO;
import com.example.org.template.excel.DTO.FetchOrganisationResponse;
import com.example.org.template.excel.DTO.OrganisationDTO;
import com.example.org.template.excel.model.Organisation;
import org.graalvm.util.CollectionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganisationService {

@Autowired
private OrganisationDAO organisationDAO;

@Transactional
public void createOrganisation(OrganisationDTO dto)throws Exception{
    if(!StringUtils.hasText(dto.getOrganisationName())){
        
        throw new Exception("No organisation name is present");
    }
    Optional<Organisation> optional = organisationDAO.findByName(dto.getOrganisationName());
    Organisation organisation = new Organisation();
    String templateName = null;
    String systemColumn = null;
    if(optional.isPresent() && !CollectionUtils.isEmpty(dto.getColumns())){
         organisation = optional.get();
         templateName = optional.get().getTemplateCol() + "^"+ String.join("^", dto.getColumns());
         systemColumn = optional.get().getSystemCol() + "^" + dto.getColumns().stream().map(String::toUpperCase).map(col->col.replace(" ","_")).collect(Collectors.joining("^"));
    }else{
         templateName = String.join("^", dto.getColumns());
         systemColumn = dto.getColumns().stream().map(String::toUpperCase).map(col->col.replace(" ","_")).collect(Collectors.joining("^"));
        organisation.setName(dto.getOrganisationName());
    }
    organisation.setCreated(LocalDate.now());
    organisation.setTemplateCol(templateName);
    organisation.setSystemCol(systemColumn);
   
   
    organisationDAO.save(organisation);
}

public List<FetchOrganisationResponse> fetchOrg(){
    List<Organisation> listOfOrg = organisationDAO.findAll();
    List<FetchOrganisationResponse> response = new ArrayList<>();
    if (CollectionUtils.isEmpty(listOfOrg)){
        return response;
    }
    
    response = listOfOrg.stream().map(obj -> new FetchOrganisationResponse(obj.getName(),
    Arrays.stream(obj.getTemplateCol().split("\\^")).collect(Collectors.toMap(col->col , col -> col.toUpperCase().replace(" ","_"))))).collect(Collectors.toList());
    return response;
}
    public void updateOrganisation(OrganisationDTO dto)throws Exception{
        if(!StringUtils.hasText(dto.getOrganisationName())){
            throw new Exception("No organisation name is present");
        }
        Optional<Organisation> optional = organisationDAO.findByName(dto.getOrganisationName());
        if(!optional.isPresent()){
            throw new Exception("No organisation is present with this name");
        }
        Organisation organisation = optional.get();
        String templateName = dto.getColumns().stream().collect(Collectors.joining("^"));
            organisation.setSystemCol(templateName);
        organisationDAO.save(organisation);
    }
}

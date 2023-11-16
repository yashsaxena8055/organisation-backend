package com.example.org.template.excel.service;

import com.example.org.template.excel.DAO.MetadataDAO;
import com.example.org.template.excel.DAO.UserDAO;
import com.example.org.template.excel.model.MetaData;
import com.example.org.template.excel.model.Organisation;
import com.example.org.template.excel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetaDataService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MetadataDAO metadataDAO;
    
    @Transactional
    public void createMetadata(List<Map<String, String>> data, Organisation organisation) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        data.stream().forEach(rows -> {
            User user = new User();
            user.setCreated(LocalDateTime.now());
            user.setOrganisation(organisation);
            user = userDAO.save(user);

            Map<String, String> map = data.get(atomicInteger.getAndIncrement());

            for (String key : map.keySet()) {
                MetaData metaData = new MetaData();
                metaData.setKey(key);
                metaData.setValue(map.get(key));
                metaData.setUser(user);
                metaData.setCreated(LocalDateTime.now());
                metadataDAO.save(metaData);
            }
        });
    }
}

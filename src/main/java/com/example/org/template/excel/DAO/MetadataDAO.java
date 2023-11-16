package com.example.org.template.excel.DAO;

import com.example.org.template.excel.model.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataDAO extends JpaRepository<MetaData,Integer> {
}

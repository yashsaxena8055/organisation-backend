package com.example.org.template.excel.DAO;

import com.example.org.template.excel.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationDAO extends JpaRepository<Organisation, Integer> {

public Optional<Organisation> findByName(String name);
}

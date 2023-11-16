package com.example.org.template.excel.DAO;

import com.example.org.template.excel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserDAO extends JpaRepository<User,Integer> {
}

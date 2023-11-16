package com.example.org.template.excel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Table
@Entity(name = "organisation")
@Getter
@Setter
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "system_col", nullable = false)
    private String systemCol;
    @Column(name = "temp_col", nullable = false)
    private String templateCol;
    @Column(name = "created", nullable = false)
    private LocalDate created;
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "organisation")
    private List<User> users;
}


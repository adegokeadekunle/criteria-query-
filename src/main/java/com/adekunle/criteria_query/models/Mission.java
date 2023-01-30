package com.adekunle.criteria_query.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Mission {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private int period;
    @ManyToMany(mappedBy = "mission")
    private List<Employee> employees;
}

package com.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employee {

    private int id;
    private String name;
    private String department;

    @Autowired
    private Skill skill;

    public Employee() {
        this.id = 1;
        this.name = "Aaliya";
        this.department = "Software Development";
    }

    public void display() {
        System.out.println("Employee Id     : " + id);
        System.out.println("Employee Name   : " + name);
        System.out.println("Department      : " + department);
        System.out.println("Skill Details   : " + skill);
    }
}

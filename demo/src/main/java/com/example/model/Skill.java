package com.example.model;

import org.springframework.stereotype.Component;

@Component
public class Skill {

    private int id;
    private String skillName;
    private String level;

    public Skill() {
        this.id = 201;
        this.skillName = "Spring Framework";
        this.level = "Intermediate";
    }

    @Override
    public String toString() {
        return "Skill [id=" + id +
               ", skillName=" + skillName +
               ", level=" + level + "]";
    }
}


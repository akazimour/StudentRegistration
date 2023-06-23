package com.akazimour.StudentRegistration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Cacheable
@Audited
public class Teacher implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDate birthDate;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Course> course;
    public Teacher() {
    }

    public Teacher(int id, String name, LocalDate birthDate, List<Course> course) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }
}

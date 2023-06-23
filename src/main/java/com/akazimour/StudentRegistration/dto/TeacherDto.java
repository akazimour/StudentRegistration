package com.akazimour.StudentRegistration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.List;


public class TeacherDto {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDate birthDate;

    private List<CourseDto> course;

    public TeacherDto() {
    }

    public TeacherDto(int id, String name, LocalDate birthDate, List<CourseDto> course) {
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

    public List<CourseDto> getCourse() {
        return course;
    }

    public void setCourse(List<CourseDto> course) {
        this.course = course;
    }
}

package com.akazimour.StudentRegistration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.List;


public class StudentDto {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private LocalDate birthDate;
    private Integer semester;

    private List<CourseDto> course;

    public StudentDto() {
    }

    public StudentDto(Integer id, String name, LocalDate birthDate, Integer semester, List<CourseDto> course) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.semester = semester;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public List<CourseDto> getCourse() {
        return course;
    }

    public void setCourse(List<CourseDto> course) {
        this.course = course;
    }
}

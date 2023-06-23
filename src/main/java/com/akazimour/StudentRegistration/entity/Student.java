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
public class Student implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private LocalDate birthDate;
    private Integer semester;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Course> course;
    private Integer usedFreeSemesters;
    private String centralId;

    public Student() {
    }

    public Student(Integer id, String name, LocalDate birthDate, Integer semester, List<Course> course, Integer usedFreeSemesters, String centralId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.semester = semester;
        this.course = course;
        this.usedFreeSemesters = usedFreeSemesters;
        this.centralId = centralId;
    }

    public Integer getUsedFreeSemesters() {
        return usedFreeSemesters;
    }

    public void setUsedFreeSemesters(Integer usedFreeSemesters) {
        this.usedFreeSemesters = usedFreeSemesters;
    }

    public String getCentralId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
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

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }
}

package com.akazimour.StudentRegistration.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class CourseDto implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    private Set<StudentDto> students;

    private Set<TeacherDto> teachers;

    public CourseDto() {
    }

    public CourseDto(int id, String name, Set<StudentDto> students, Set<TeacherDto> teachers) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.teachers = teachers;
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

    public Set<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDto> students) {
        this.students = students;
    }

    public Set<TeacherDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherDto> teachers) {
        this.teachers = teachers;
    }
}

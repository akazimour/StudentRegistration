package com.akazimour.StudentRegistration.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.springframework.context.annotation.ComponentScan;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@ComponentScan
@Entity
@NamedEntityGraph(name = "Course.students", attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(name = "Course.teachers", attributeNodes = @NamedAttributeNode("teachers"))
@Audited
public class Course implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @ManyToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private Set<Student> students;
    @ManyToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private Set<Teacher> teachers;

    public Course() {
    }

    public Course(int id, String name, Set<Student> students, Set<Teacher> teachers) {
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}

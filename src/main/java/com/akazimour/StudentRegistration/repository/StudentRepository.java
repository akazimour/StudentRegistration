package com.akazimour.StudentRegistration.repository;

import com.akazimour.StudentRegistration.entity.Student;
import com.akazimour.StudentRegistration.entity.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student>, QuerydslPredicateExecutor<Student> {


    @Query("SELECT s FROM Student s WHERE s.id =:studentId")
    Optional<Student> findStudentById(Integer studentId);

    Optional<Student> findByCentralId(String centralId);
}


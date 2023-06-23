package com.akazimour.StudentRegistration.repository;

import com.akazimour.StudentRegistration.entity.Course;
import com.akazimour.StudentRegistration.entity.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Integer>, JpaSpecificationExecutor<Teacher>, QuerydslPredicateExecutor<Teacher> {


    @Query("SELECT t FROM Teacher t WHERE t.id =:teacherId")
    Optional<Teacher> findTeacherById(Integer teacherId);



}

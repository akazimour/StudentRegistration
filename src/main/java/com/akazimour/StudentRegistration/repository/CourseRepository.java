package com.akazimour.StudentRegistration.repository;
import com.akazimour.StudentRegistration.entity.Course;
import com.akazimour.StudentRegistration.entity.QCourse;
import com.akazimour.StudentRegistration.entity.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Iterator;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Integer>, JpaSpecificationExecutor<Course>, QuerydslPredicateExecutor<Course>, QuerydslBinderCustomizer<QCourse>,QueryDslWithEntityGraphRepository<Course,Integer>{

       @Override
        default void customize(QuerydslBindings bindings, QCourse course){
        bindings.bind(course.name).first(((path, value) -> path.startsWithIgnoreCase(value)));
        bindings.bind(course.id).first(((path, value) -> path.eq(value)));
        bindings.bind(course.teachers.any().name).first(((path, value) -> path.startsWithIgnoreCase(value)));
        bindings.bind(course.students.any().id).first(((path, value) -> path.eq(value)));
        bindings.bind(course.students.any().semester).all(((path, values) -> {
            if (values.size()!=2)
                return Optional.empty();
            Iterator<? extends Integer> iterator = values.iterator();
            Integer start = iterator.next();
            Integer end = iterator.next();
          return Optional.of(path.between(start,end));
        }
        ));
        }
       @EntityGraph(attributePaths = "students")
       @Query("SELECT c FROM Course c ")
       List<Course> findAllWithStudents();

       @EntityGraph(attributePaths = "teachers")
       @Query("SELECT c FROM Course c ")
       List<Course> findAllWithTeachers();

        @EntityGraph(attributePaths = "students")
        Optional<List<Course>> findAllCourseByTeachersId(Integer teacherId);

        @EntityGraph(attributePaths = {"teachers","students"})
        @Query("SELECT c FROM Course c WHERE c.id =:courseId ")
        Optional<Course> findByIdWithTeachersAndStudents(Integer courseId);

        @EntityGraph(attributePaths = "teachers")
        Optional<List<Course>>findAllCourseByStudentsId(Integer studentId);


}

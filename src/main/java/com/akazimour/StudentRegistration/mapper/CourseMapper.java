package com.akazimour.StudentRegistration.mapper;


import com.akazimour.StudentRegistration.dto.CourseDto;
import com.akazimour.StudentRegistration.dto.StudentDto;
import com.akazimour.StudentRegistration.dto.TeacherDto;
import com.akazimour.StudentRegistration.entity.Course;
import com.akazimour.StudentRegistration.entity.Student;
import com.akazimour.StudentRegistration.entity.Teacher;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "students.course", ignore = true)
    @Mapping(target = "teachers.course", ignore = true)
    Course courseDtoToCourse (CourseDto courseDto);

    List<CourseDto> iterableCourseToCourseDto(Iterable<Course> all);
    @Named(value = "summary")
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    CourseDto courseToSummaryCourseDto(Course course);

    @Named("basic")
    CourseDto courseToCourseDto(Course course);

    @IterableMapping(qualifiedByName = "summary")
    List<CourseDto> coursesToSummaryCourseDtos (List<Course> courses);

    @Named(value = "basic")
    List<CourseDto> coursesToCourseDtos (List<Course> courses);

    List<Course> courseDtosToCourses(List<CourseDto> courseDtos);

    Set<Course> courseDtosToSetCourses(Set<CourseDto> courseDtos);

    @Mapping(target = "course", ignore = true)
    StudentDto studentToStudentDto (Student student);

    @Mapping(target = "course", ignore = true)
    TeacherDto teacherToTeacherDto (Teacher teacher);


}

package com.akazimour.StudentRegistration.mapper;

import com.akazimour.StudentRegistration.dto.CourseDto;
import com.akazimour.StudentRegistration.dto.StudentDto;
import com.akazimour.StudentRegistration.dto.TeacherDto;
import com.akazimour.StudentRegistration.entity.Course;
import com.akazimour.StudentRegistration.entity.Student;
import com.akazimour.StudentRegistration.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDto teacherToTeacherDto(Teacher teacher);
    @Mapping(target = "teachers", ignore = true)
    CourseDto courseToCourseDto(Course course);

    @Mapping(target = "course", ignore = true)
    StudentDto studentToStudentDto(Student student);

    @Named("sumTeacher")
    @Mapping(target = "course", ignore = true)
    TeacherDto teacherToSummaryTeacherDto(Teacher teacher);

    Set<Teacher> teacherDtosToSetTeachers(Set<TeacherDto> teacherDtos);
}

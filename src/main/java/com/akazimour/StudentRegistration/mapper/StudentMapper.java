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

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto StudentToStudentDto(Student student);
    @Named("sumStudent")
    @Mapping(target = "course", ignore = true)
    StudentDto StudentToSummaryStudentDto (Student student);
    @Mapping(target = "students", ignore = true)
    CourseDto courseToDto (Course course);
    @Mapping(target = "course", ignore = true)
    TeacherDto teacherToDto (Teacher teacher);

    Set<Student> studentDtosToSetStudents(Set<StudentDto> studentDtos);



}

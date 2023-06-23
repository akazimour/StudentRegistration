package com.akazimour.StudentRegistration.controller;

import com.akazimour.StudentRegistration.dto.StudentDto;
import com.akazimour.StudentRegistration.mapper.StudentMapper;
import com.akazimour.StudentRegistration.repository.StudentRepository;
import com.akazimour.StudentRegistration.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/student")
public class StudentController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/search/{id}")
    public StudentDto searchStudentById(@PathVariable Integer id, @RequestParam Optional<Boolean> full){
        boolean isFull = full.orElse(false);
        return  isFull ?  studentMapper.StudentToStudentDto(courseService.findStudentWithId(id))
                : studentMapper.StudentToSummaryStudentDto(studentRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found with id "+id)));
    }

}

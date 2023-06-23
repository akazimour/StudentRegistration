package com.akazimour.StudentRegistration.controller;

import com.akazimour.StudentRegistration.dto.TeacherDto;
import com.akazimour.StudentRegistration.mapper.TeacherMapper;
import com.akazimour.StudentRegistration.repository.TeacherRepository;
import com.akazimour.StudentRegistration.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/teacher")
public class TeacherController {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseService courseService;
    @GetMapping("/search/{id}")
    public TeacherDto searchTeacherById(@PathVariable Integer id, @RequestParam Optional<Boolean> full){
        boolean isFull = full.orElse(false);
        return  isFull ?  teacherMapper.teacherToTeacherDto(courseService.findTeacherWithId(id))
                : teacherMapper.teacherToSummaryTeacherDto(teacherRepository.findTeacherById(id).get());
    }
}

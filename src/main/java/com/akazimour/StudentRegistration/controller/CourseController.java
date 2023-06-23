package com.akazimour.StudentRegistration.controller;
import com.akazimour.StudentRegistration.dto.CourseDto;
import com.akazimour.StudentRegistration.dto.StudentDto;
import com.akazimour.StudentRegistration.dto.TeacherDto;
import com.akazimour.StudentRegistration.entity.Course;
import com.akazimour.StudentRegistration.entity.HistoryData;
import com.akazimour.StudentRegistration.mapper.CourseMapper;
import com.akazimour.StudentRegistration.mapper.StudentMapper;
import com.akazimour.StudentRegistration.mapper.TeacherMapper;
import com.akazimour.StudentRegistration.repository.CourseRepository;
import com.akazimour.StudentRegistration.repository.StudentRepository;
import com.akazimour.StudentRegistration.repository.TeacherRepository;
import com.akazimour.StudentRegistration.service.CourseService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseMapper courseMapper;




    @PostMapping("/search")
    public List<CourseDto> dynamicSearch(@RequestBody CourseDto courseDto, @RequestParam Optional<Boolean> full, @SortDefault("id") Pageable pageable) {
        boolean isFull = full.orElse(false);
        if (isFull) {
            List<Course> courseByQueryDsl = courseService.findCourseByQueryDsl(courseMapper.courseDtoToCourse(courseDto),pageable);
            return courseMapper.coursesToCourseDtos(courseByQueryDsl);
        }else {
            List<Course> summaryResult = courseService.findCourseByQueryDsl(courseMapper.courseDtoToCourse(courseDto),pageable);
           return courseMapper.coursesToSummaryCourseDtos(summaryResult);
        }
    }
    @GetMapping("/search")
    public List<CourseDto> dynamicSearch2(@QuerydslPredicate(root = Course.class) Predicate predicate, @SortDefault("id") Pageable pageable,@RequestParam Optional<Boolean> full) {
        boolean isFull = full.orElse(false);
        if (isFull) {
            return courseMapper.iterableCourseToCourseDto(courseService.searchCourses(predicate, pageable));
        }else
           return courseMapper.coursesToSummaryCourseDtos((List<Course>) courseRepository.findAll(predicate,pageable)) ;
    }
    
    @GetMapping
    public List<CourseDto> getAllCourses(@RequestParam Optional<Boolean> full) {
        boolean isFull = full.orElse(false);
        if (isFull) {
            List<Course> allWithStudentsAndTeachers = courseService.findAllWithRelationships();
            return courseMapper.coursesToCourseDtos(allWithStudentsAndTeachers);
        } else {
            List<Course> all = courseRepository.findAll();
            return courseMapper.coursesToSummaryCourseDtos(all);
        }
}
    @GetMapping("/{id}")
    public CourseDto searchCourseById(@PathVariable Integer id,@RequestParam Optional<Boolean> full){
        boolean isFull = full.orElse(false);
        return  isFull ? courseMapper.courseToCourseDto(courseRepository.findByIdWithTeachersAndStudents(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found with id "+id)))
                : courseMapper.courseToSummaryCourseDto(courseRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found with id "+id)));
    }

    @GetMapping("/{id}/history")
    public List<HistoryData<CourseDto>> getHistoryById(@PathVariable Integer id,@RequestParam Optional<Boolean> full){
        List<HistoryData<Course>> courseHistory = courseService.getCourseHistory(id);
        List<HistoryData<CourseDto>> courseDtosWithHystory = new ArrayList<>();

        courseHistory.forEach(courseHistoryData -> {courseDtosWithHystory.add(new HistoryData<>(courseMapper.courseToCourseDto(courseHistoryData.getData()),
                courseHistoryData.getRevisionType(),
                courseHistoryData.getRevision(),
                courseHistoryData.getDate()
                ));
        });

        return courseDtosWithHystory;
    }

    @PutMapping("/{id}")
    public CourseDto modifyCourse(@RequestBody CourseDto courseDto, @PathVariable Integer id){
     return  courseMapper.courseToCourseDto(courseService.updateCourse(courseDto,id));
    }





}
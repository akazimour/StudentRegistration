package com.akazimour.StudentRegistration.service;

import com.akazimour.StudentRegistration.dto.CourseDto;
import com.akazimour.StudentRegistration.entity.*;
import com.akazimour.StudentRegistration.mapper.StudentMapper;
import com.akazimour.StudentRegistration.mapper.TeacherMapper;
import com.akazimour.StudentRegistration.repository.CourseRepository;
import com.akazimour.StudentRegistration.repository.StudentRepository;
import com.akazimour.StudentRegistration.repository.TeacherRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    FreeSemesterService freeSemesterService;
    @Autowired
    TaskScheduler taskScheduler;
    @Autowired
    TaskExecutor taskExecutor;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @PersistenceContext
    EntityManager em;




    public ArrayList<Course> findCourseByQueryDsl(Course example, Pageable pageable) {

        int id = example.getId();
        String courseName = example.getName();
        Teacher teacher1 = example.getTeachers().stream().filter(teacher -> teacher.getId()>0).findAny().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Student with id "+ id +" id has not been found!"));
        String teacherName = teacher1.getName();
        Student studentWithId = example.getStudents().stream().filter(student -> student.getId()!=null).findAny().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Student with id "+ id +" id has not been found!"));
        Integer studentId = studentWithId.getId();
        Integer semesterNumber = studentWithId.getSemester();
        ArrayList<Predicate> collectedPredicates = new ArrayList<Predicate>();

        QCourse course = QCourse.course;

        if (id > 0){
            collectedPredicates.add(course.id.eq(id));
        }
        if (StringUtils.hasText(courseName)){
            collectedPredicates.add(course.name.startsWithIgnoreCase(courseName));
        }
        if (StringUtils.hasText(teacherName)){
            collectedPredicates.add(course.teachers.any().name.startsWithIgnoreCase(teacherName));
        }
        if (studentId != null){
            collectedPredicates.add(course.students.any().id.eq(id));
        }
        if (semesterNumber != null){
            collectedPredicates.add(course.students.any().semester.between(1, 7));
        }
        ArrayList<Course> courses = Lists.newArrayList(courseRepository.findAll((ExpressionUtils.allOf(collectedPredicates)),pageable).getContent());
        return courses;
    }


   public List<Course> searchCourses(Predicate predicate,Pageable pageable) {
       Page<Course> all = courseRepository.findAll(predicate, pageable);
       BooleanExpression in = QCourse.course.in(all.getContent());
       List<Course> courses = (List<Course>) courseRepository.findAll(in, Sort.unsorted());
       courses = courseRepository.findAll(in, "Course.teachers", pageable.getSort());
       return courses;
   }


    @Transactional
    public List<Course> initDbWithCourses(){

        List<Course> generatedCourses = new ArrayList<>();
        Set<Student> firstStudentList = new HashSet<>();
        Set<Student> secondStudentList = new HashSet<>();
        Set<Student> thirdStudentList = new HashSet<>();

        Set<Teacher> firstTeacherList = new HashSet<>();
        Set<Teacher> secondTeacherList = new HashSet<>();
        Set<Teacher> thirdTeacherList = new HashSet<>();

        Student student1 = new Student();
        student1.setId(1);
        student1.setName("John Smith");
        student1.setBirthDate((LocalDate.of(1990,6,5)));
        student1.setSemester(1);
        student1.setCentralId("SMTH");


        Student student2 = new Student();
        student2.setId(2);
        student2.setName("Sarah Conor");
        student2.setBirthDate(LocalDate.of(1990,5,10));
        student2.setSemester(3);
        student2.setCentralId("CNR");


        Student student3 = new Student();
        student3.setId(3);
        student3.setName("John Doe");
        student3.setBirthDate(LocalDate.of(1988,4,12));
        student3.setSemester(4);
        student3.setCentralId("JHND");


        Student student4 = new Student();
        student4.setId(4);
        student4.setName("Dave Smith");
        student4.setBirthDate(LocalDate.of(1984,7,22));
        student4.setSemester(2);
        student4.setCentralId("DSMTH");


        Student student5 = new Student();
        student5.setId(5);
        student5.setName("Jenifer Smith");
        student5.setBirthDate(LocalDate.of(1981,1,14));
        student5.setSemester(5);
        student5.setCentralId("JNSMTH");



        firstStudentList.add(student1);
        firstStudentList.add(student3);
        firstStudentList.add(student5);

        secondStudentList.add(student1);
        secondStudentList.add(student2);
        secondStudentList.add(student4);

        thirdStudentList.add(student2);
        thirdStudentList.add(student5);
        thirdStudentList.add(student3);
        thirdStudentList.add(student4);

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setName("Alan James");
        teacher1.setBirthDate(LocalDate.of(1980,3,17));

        Teacher teacher2 = new Teacher();
        teacher2.setId(2);
        teacher2.setName("Emily rose");
        teacher2.setBirthDate(LocalDate.of(1987,2,5));

        Teacher teacher3 = new Teacher();
        teacher3.setId(3);
        teacher3.setName("Angelina Jolie");
        teacher3.setBirthDate(LocalDate.of(1985,6,19));

        Teacher teacher4 = new Teacher();
        teacher4.setId(4);
        teacher4.setName("Mike Hollister");
        teacher4.setBirthDate(LocalDate.of(1980,4,21));

        Teacher teacher5 = new Teacher();
        teacher5.setId(5);
        teacher5.setName("Mike Hollister");
        teacher5.setBirthDate(LocalDate.of(1983,10,4));

        firstTeacherList.add(teacher1);
        secondTeacherList.add(teacher1);
        secondTeacherList.add(teacher2);
        thirdTeacherList.add(teacher3);
        thirdTeacherList.add(teacher4);
        thirdTeacherList.add(teacher2);


        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Mechanics");

        Course course2 = new Course();
        course2.setId(2);
        course2.setName("Technical Drawing");

        Course course3 = new Course();
        course3.setId(3);
        course3.setName("Mechatronics");

        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        ArrayList courses1 =new ArrayList<>();
        ArrayList courses2 =new ArrayList<>();
        ArrayList courses3 =new ArrayList<>();
        ArrayList courses4 =new ArrayList<>();

        courses1.add(course1);
        courses1.add(course2);
        courses2.add(course2);
        courses2.add(course3);
        courses3.add(course1);
        courses3.add(course3);
        courses4.add(course3);
        student1.setCourse(courses1);
        student2.setCourse(courses2);
        student3.setCourse(courses3);
        student4.setCourse(courses2);
        student5.setCourse(courses3);

        teacher1.setCourse(courses1);
        teacher2.setCourse(courses2);
        teacher3.setCourse(courses3);
        teacher4.setCourse(courses3);



        course1.setStudents(firstStudentList);
        course2.setStudents(secondStudentList);
        course3.setStudents(thirdStudentList);

        course1.setTeachers(firstTeacherList);
        course2.setTeachers(secondTeacherList);
        course3.setTeachers(thirdTeacherList);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);
        teacherRepository.save(teacher4);
        teacherRepository.save(teacher5);
        generatedCourses.add(course1);
        generatedCourses.add(course2);
        generatedCourses.add(course3);

        return generatedCourses;
    }
    @Transactional
    @Cacheable("allCoursesWithRelationships")
    public List<Course> findAllWithRelationships() {
    List<Course> allWithStudents = courseRepository.findAllWithStudents();
    allWithStudents = courseRepository.findAllWithTeachers();
    return allWithStudents;
}

    @Transactional
    public void deleteDb() {
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
    }

    @Transactional
    public void deleteAudTables() {
        jdbcTemplate.update("DELETE FROM course_aud");
        jdbcTemplate.update("DELETE FROM student_aud");
        jdbcTemplate.update("DELETE FROM teacher_aud");
    }

    @Transactional
    public Teacher findTeacherWithId(Integer id){
   Teacher teacherById = teacherRepository.findTeacherById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Teacher not found with id "+id));
   List<Course> courses = courseRepository.findAllCourseByTeachersId(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found with id "+id));
   teacherById.setCourse(courses);
   return teacherById;
}
    @Transactional
    public Student findStudentWithId(Integer id){
        Student student = studentRepository.findStudentById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id " + id));
        List<Course> allCoursesByStudentsId = courseRepository.findAllCourseByStudentsId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id " + id));;
        student.setCourse(allCoursesByStudentsId);
        return student;
    }

//@Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}"/*cron = "10 * * * * *"*/)
    public Runnable updateFreeSemesters(){
    List<Student> all = studentRepository.findAll();
    all.forEach(student -> {
            int usedFreeSemesters = freeSemesterService.requestFreeSemesters(student.getCentralId());
            updateUsedSemesters(student, usedFreeSemesters);
            ;});
    return null;
}
    private void updateUsedSemesters(Student student, int usedFreeSemesters) {
        System.out.println(usedFreeSemesters);
        student.setUsedFreeSemesters(usedFreeSemesters);
        studentRepository.save(student);
        System.out.println("FreeSemesterNumber has been updated for student");
    }
   public void recallUpdates(long delay)  {

        //Solution 1 ez a metódus újra hívja az update metóust a beépített hiba dobása után, maga hiba dobás csak üzenet formájában van kiírva a konzolra
       ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
       scheduler.schedule(updateFreeSemesters(),delay,TimeUnit.MILLISECONDS);
       scheduler.shutdown();

       //Solution 2 itt a dobott hiba is megjelenik a konzolon majd újra hívódik az update metódus
       /* ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleWithFixedDelay(() -> {
        updateFreeSemesters();
        }, delay);*/

       }
       public Course updateCourse(CourseDto courseDto, Integer id){
           Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course can not be found with id " + id));
           course.setId(courseDto.getId());
           course.setName(courseDto.getName());
           course.setStudents(studentMapper.studentDtosToSetStudents(courseDto.getStudents()));
           course.setTeachers(teacherMapper.teacherDtosToSetTeachers(courseDto.getTeachers()));
           courseRepository.save(course);
           return course;
       }
        @Transactional
       public List<HistoryData<Course>> getCourseHistory(Integer id){
            List resultList = AuditReaderFactory.get(em)
                    .createQuery()
                    .forRevisionsOfEntity(Course.class, false, true)
                    .add(AuditEntity.property("id").eq(id))
                    .getResultList().stream().map(o->{
                        Object[] objArray = (Object[])o;
                        DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
                        Course course = (Course) objArray[0];
                        course.getName().length();
                        course.getStudents().size();
                        course.getTeachers().size();
                        return new HistoryData<Course>(
                                course,
                                (RevisionType)objArray[2],
                                defaultRevisionEntity.getId(),
                                defaultRevisionEntity.getRevisionDate()
                        );
                    }).toList();

            return resultList;
        }

    }



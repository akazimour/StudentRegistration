package com.akazimour.StudentRegistration;

import com.akazimour.StudentRegistration.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudentRegistrationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StudentRegistrationApplication.class, args);
	}
	@Autowired
	CourseService courseService;

	@Override
	public void run(String... args) throws Exception {
		/*courseService.deleteDb();
		courseService.deleteAudTables();*/
		courseService.initDbWithCourses();
	}
}

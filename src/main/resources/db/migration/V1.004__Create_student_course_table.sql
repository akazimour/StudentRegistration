create table student_course (course_id integer not null, students_id integer not null);

alter table if exists student_course add constraint FK_students_id foreign key (course_id) references course;
alter table if exists student_course add constraint FK_course_id foreign key (students_id) references student;


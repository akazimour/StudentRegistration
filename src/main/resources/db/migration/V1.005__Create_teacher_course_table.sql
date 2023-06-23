create table teacher_course (course_id integer not null, teachers_id integer not null);

alter table if exists teacher_course add constraint FK_teachers_id foreign key (course_id) references course;
alter table if exists teacher_course add constraint FK_course_id foreign key (teachers_id) references teacher;
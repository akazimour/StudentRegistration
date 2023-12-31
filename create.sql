
create sequence course_seq start with 1 increment by 50;
create sequence revinfo_seq start with 1 increment by 50;
create sequence student_seq start with 1 increment by 50;
create sequence teacher_seq start with 1 increment by 50;
create table course (id integer not null, name varchar(255), primary key (id));
create table course_aud (id integer not null, rev integer not null, revtype smallint, name varchar(255), primary key (rev, id));
create table revinfo (rev integer not null, revtstmp bigint, primary key (rev));
create table student (id integer not null, birth_date date, central_id varchar(255), name varchar(255), semester integer, used_free_semesters integer, primary key (id));
create table student_aud (id integer not null, rev integer not null, revtype smallint, birth_date date, central_id varchar(255), name varchar(255), semester integer, used_free_semesters integer, primary key (rev, id));
create table student_course (students_id integer not null, course_id integer not null);
create table student_course_aud (rev integer not null, students_id integer not null, course_id integer not null, revtype smallint, primary key (rev, students_id, course_id));
create table teacher (id integer not null, birth_date date, name varchar(255), primary key (id));
create table teacher_aud (id integer not null, rev integer not null, revtype smallint, birth_date date, name varchar(255), primary key (rev, id));
create table teacher_course (teachers_id integer not null, course_id integer not null);
create table teacher_course_aud (rev integer not null, teachers_id integer not null, course_id integer not null, revtype smallint, primary key (rev, teachers_id, course_id));
alter table if exists course_aud add constraint FK7wota7b9g9bu9by751v8r8j65 foreign key (rev) references revinfo;
alter table if exists student_aud add constraint FKj009xm0wjydklskl2dgnfyyjq foreign key (rev) references revinfo;
alter table if exists student_course add constraint FKejrkh4gv8iqgmspsanaji90ws foreign key (course_id) references course;
alter table if exists student_course add constraint FK45duwtec6os4bkyug7b4ynajr foreign key (students_id) references student;
alter table if exists student_course_aud add constraint FKorgxh6fw5qx5f60d6oew7v1iw foreign key (rev) references revinfo;
alter table if exists teacher_aud add constraint FKsg6tnk689ja9qg8qhnyarygx5 foreign key (rev) references revinfo;
alter table if exists teacher_course add constraint FKp8bco6842vkqh13y4759ib7tk foreign key (course_id) references course;
alter table if exists teacher_course add constraint FKehexn2qiowsefhl392kosexg5 foreign key (teachers_id) references teacher;
alter table if exists teacher_course_aud add constraint FK6f5l42amlkdn88jgk3b02we2m foreign key (rev) references revinfo;

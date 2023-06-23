create sequence student_seq start with 1 increment by 50;
create table student (birth_date date, id integer not null, semester integer, name varchar(255), primary key (id));


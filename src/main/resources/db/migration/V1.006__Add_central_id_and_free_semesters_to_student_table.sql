alter table if exists student add column central_id varchar(255);
alter table if exists student add column used_free_semesters integer DEFAULT 0;

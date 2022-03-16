ALTER TABLE student_aud
    ADD COLUMN birthdate date,
    ADD COLUMN name varchar(255);
ALTER TABLE teacher_aud
    ADD COLUMN birthdate date,
    ADD COLUMN name varchar(255);
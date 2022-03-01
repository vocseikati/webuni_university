create sequence hibernate_sequence start WITH 1 increment BY 1;
create table student (id int4 not null, birthdate date, name varchar(255), semester int4 not null, primary key (id));
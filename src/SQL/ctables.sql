DROP TABLE Inspection;
DROP TABLE Staff;
DROP TABLE Invoice;
DROP TABLE Lease;
DROP TABLE Room;
DROP TABLE ResHall;
DROP TABLE Student;
DROP TABLE Advisor;

CREATE TABLE Advisor (	
	advisor_id numeric(10) PRIMARY KEY not null,
	first_name varchar2(20) not null,
	last_name varchar2(20) not null,
	position varchar2(20) not null,
	dept_name varchar2(50) not null,
	phone varchar2(50),
	email varchar2(100)
 );
 
 CREATE TABLE Staff (
	staff_id numeric(10) PRIMARY KEY not null,
	first_name varchar2(20) not null,
	last_name varchar2(20) not null,
	dob varchar2(10) not null,
	gender varchar2(1) not null,
	job_title varchar2(30) not null,
	location numeric(10) not null
 );
 
 CREATE TABLE Student (	
	student_id numeric(10) PRIMARY KEY not null,
	first_name varchar2(20) not null,
	last_name varchar2(20) not null,
	address varchar2(50) not null,
	phone varchar2(50),
	email varchar2(100),
	gender varchar2(1) not null,
	dob varchar2(10) not null,
	category varchar2(20) not null,
	major varchar2(30) not null,
	minor varchar2(30),
	advisor_id numeric(10) not null
 );
 
 CREATE TABLE Lease(
	lease_id numeric(10) PRIMARY KEY not null,
	res_apt_id numeric(10) not null,
	room_no numeric(5) not null,
	student_id numeric(10) not null,
	rate numeric(10) not null,
	start_date varchar2(10) not null,
	duartion varchar2(10) not null,
	first_name varchar2(20) not null,
	last_name varchar2(20) not null
 );
 
 CREATE TABLE ResHall (
	hall_id numeric(10) PRIMARY KEY not null,
	name varchar(30) not null,
	address varchar2(50) not null,
	phone varchar2(50) not null
 );
 
 CREATE TABLE Room(
	res_apt_id numeric(10) not null,
	room_no numeric(5) not null,
	rate numeric(10) not null,
	CONSTRAINT pk_room PRIMARY KEY (res_apt_id, room_no)
 );
 
 CREATE TABLE Invoice (
	invoice_id numeric(10) PRIMARY KEY not null,
	lease_id numeric(10),
	semester numeric(10) not null,
	due_date varchar2(10) not null,
	paid_date varchar2(10)
 );
 
 CREATE TABLE Inspection(
	inspection_id numeric(10) PRIMARY KEY not null,
	res_apt_no numeric(10),
	room_no numeric(10),
	staff_id numeric(10),
	inspection_date varchar2(10),
	condition varchar2(20),
	action_taken varchar2(50)
 );

DROP TABLE Inspection;
DROP TABLE Staff;
DROP TABLE Invoice;
DROP TABLE Lease;
DROP TABLE Room;
DROP TABLE ResHall;
DROP TABLE Apartment;
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
	gender varchar2(1) not null,
	dob varchar2(10) not null,
	job_title varchar2(20) not null,
	location varchar2(30) not null
 );
 
 CREATE TABLE Student (	
	student_id numeric(10) PRIMARY KEY not null,
	advisor_id numeric(10) references Advisor (advisor_id) not null,
	first_name varchar2(20) not null,
	last_name varchar2(20) not null,
	address varchar2(50) not null,
	phone varchar2(50),
	email varchar2(100),
	gender varchar2(1) not null,
	dob varchar2(10) not null,
	category varchar2(20) not null,
	major varchar2(30) not null,
	minor varchar2(30)
 );
 
 CREATE TABLE Lease(
	lease_id numeric(10) PRIMARY KEY not null,
	student_id numeric(10) references Student(student_id) not null,
	rate numeric(10) not null,
	start_date varchar2(10) not null,
	duartion varchar2(10) not null
 );
 
 CREATE TABLE ResHall (
	hall_id numeric(10) PRIMARY KEY not null,
	name varchar(30) not null,
	address varchar2(50) not null,
	phone varchar2(50) not null
 );
 
 CREATE TABLE Apartment(
	apt_id numeric(10) PRIMARY KEY not null,
	name varchar(30) not null,
	address varchar2(50) not null
 );
 
 CREATE TABLE Room(
	room_id numeric(10) PRIMARY KEY not null,
	hall_id numeric(10) references ResHall(hall_id),
	apt_id numeric(10) references Apartment(apt_id),
	room_no numeric(5) not null,
	rate numeric(10) not null
 );
 
 CREATE TABLE Invoice (
	invoice_id numeric(10) PRIMARY KEY not null,
	lease_id numeric(10) references Lease(lease_id),
	semester varchar2(10) not null,
	due_date varchar2(10) not null,
	paid_date varchar2(10) not null
 );
 
 CREATE TABLE Inspection(
	inspection_id numeric(10) PRIMARY KEY not null,
	staff_id numeric(10) references Staff(staff_id),
	room_id numeric(10) references Room(room_id),
	condition varchar2(20),
	action_takes varchar2(50)
	
 );
 
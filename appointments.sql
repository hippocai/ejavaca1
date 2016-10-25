drop database if exists appointments;

create database appointments;

use appointments;

create table people (
	pid varchar(8) not null primary key,
	name varchar(128) not null,
	email varchar(128) not null
);

create table appointment (
	appt_id int not null auto_increment primary key,
	description mediumtext not null,
	appt_date datetime not null,
	pid varchar(8) not null,
	constraint fk_pid foreign key (pid) references people(pid)
);

-- Dumping data for table `people`
LOCK TABLES `people` WRITE;
INSERT INTO `people` VALUES ('0e4d8b2c','Barney Rubble','barney@gmail.com'),('e1e1ff08','Fred Flintstone','fred@bedrock.com');
UNLOCK TABLES;

-- Dumping data for table `appointment`
LOCK TABLES `appointment` WRITE;
INSERT INTO `appointment` VALUES (5,'Lunch with Barney','2016-10-28 12:30:00','e1e1ff08'),(6,'Team meeting','2016-10-27 09:00:00','e1e1ff08'),(7,'Meeting','2016-10-21 10:00:00','e1e1ff08'),(8,'Holiday - Tokyo','2016-10-30 07:00:00','e1e1ff08'),(9,'Lunch with Fred','2016-10-28 12:30:00','0e4d8b2c'),(10,'Servicing the car','2016-10-25 08:00:00','0e4d8b2c'),(11,'Trick or treat','2016-10-31 19:00:00','0e4d8b2c');
UNLOCK TABLES;

create table if not exists Student(
                                      id varchar(20) primary key,
                                      name varchar(100) not null

);
create table if not exists Attendance(
                                         id int primary key auto_increment,
                                         student_id varchar(20) not null,
                                         `status` enum('IN','OUT') not null,
                                         stamp datetime not null,
                                         constraint fk_student_id1 foreign key (student_id) references Student(id)
);
create table if not exists Picture(
                                      student_id varchar(20) not null,
                                      picture mediumblob  not null,
                                      constraint fk_student_id2 foreign key (student_id) references Student(id)
);
create table if not exists User(
                                   user_name varchar(50) primary key,
                                   `password` varchar(100) not null,
                                   full_name varchar(100) not null
);

-- insert into User (user_name, password, full_name) values
--                 ('Kasun','123','Kasun Bandara');


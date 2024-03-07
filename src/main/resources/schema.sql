DROP table IF EXISTS EMPLOYEE;

create table EMPLOYEE
(
    id long PRIMARY KEY auto_increment,
    empName         	varchar(255),
    empDeptName         varchar(255),
    empTelNo         varchar(20),
    empMail         varchar(25)
);

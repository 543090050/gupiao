CREATE DATABASE gupiao;

CREATE TABLE gongsi (
    id varchar (32),
    name varchar(100)
);

CREATE TABLE xiangxi (
    id varchar(40),
    time varchar(100),
    tougu varchar(100),
    gongsi_id varchar(32)
);
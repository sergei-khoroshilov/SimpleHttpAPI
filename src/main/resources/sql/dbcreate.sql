create database if not exists SOCCER_STATS
	character set = 'utf8';

use SOCCER_STATS;

create table if not exists MATCH_INFO
(
	ID int auto_increment primary key,
	MATCH_DATE datetime not null,
	HOME_COMMAND varchar(64) not null,
	GUEST_COMMAND varchar(64) not null,
	SCORE varchar(8) not null
);

-- Users

-- TODO encrypt password
create table if not exists USER
(
	ID int auto_increment primary key,
	LOGIN varchar(32) not null unique,
	PASSWORD varchar(128) not null
);

insert into USER (LOGIN, PASSWORD)
select 'user', '1234'
from dual
where not exists (select * from USER);

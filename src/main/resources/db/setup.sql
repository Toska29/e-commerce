create database phoenix_db;

create user 'phoenix_user'@'localhost' identified by 'pass_123';
grant all privileges on phoenix_db.* to 'phoenix_user'@'localhost';
flush privileges;


#postgreSql
create database phoenix_db;
create user phoenix_user with password 'pass_123';
grant all privileges on database phoenix_db to phoenix_user;
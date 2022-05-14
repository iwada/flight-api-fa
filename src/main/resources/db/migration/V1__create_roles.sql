CREATE TABLE IF NOT EXISTS roles(
  id Integer Primary Key Generated Always as Identity,
  name VARCHAR(50)
);

 INSERT INTO roles(name) VALUES('ROLE_USER');
 INSERT INTO roles(name) VALUES('ROLE_ADMIN');
 
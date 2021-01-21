-- Security tables -User and role are connected via  user_role table foreign keys

CREATE TABLE security_role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description varchar(100) DEFAULT NULL,
  role_name varchar(100) DEFAULT NULL
);

CREATE TABLE security_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL
);

CREATE TABLE user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT FK_SECURITY_USER_ID FOREIGN KEY (user_id) REFERENCES security_user (id),
  CONSTRAINT FK_SECURITY_ROLE_ID FOREIGN KEY (role_id) REFERENCES security_role (id)
);

-- Customer

CREATE TABLE customer(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   first_Name VARCHAR(50) NOT NULL,
   last_Name VARCHAR(50) NOT NULL,
   dob date NOT NULL,
   email VARCHAR(20) NOT NULL,
   phone VARCHAR(10) NOT NULL,
   address VARCHAR(50) NOT NULL,
   level VARCHAR(16)
);
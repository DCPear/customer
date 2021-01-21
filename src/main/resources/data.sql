-- Customer data
insert into customer(first_Name, last_Name,  dob,  email, phone, address, level) values
(
  'John',
  'Smith',
  '1965-09-08',
  'me@me.com',
  '5678499',
  '34 Tasman',
  'Gold'
);
insert into customer(first_Name, last_Name,  dob,  email, phone, address, level) values
(
  'Jane',
  'Doe',
  '1974-06-09',
  'me@me.com',
  '5678567',
  '34 my road',
  'Silver'
);

-- Security data
-- USER
-- hashed password: letmein
INSERT INTO security_user (id, username, password, first_name, last_name) VALUES
(1,  'admin', '$2y$12$LtJY9Q5.U3IK0jBeZokkueMzbbWjMVSE2uN49GkeX.fGGIA5QBRUe', 'Administrator', 'Adminstrator'),
(2,  'Hazel', '$2y$12$LtJY9Q5.U3IK0jBeZokkueMzbbWjMVSE2uN49GkeX.fGGIA5QBRUe', 'Hazel', 'Nutt'),
(3,  'Clark', '$2y$12$LtJY9Q5.U3IK0jBeZokkueMzbbWjMVSE2uN49GkeX.fGGIA5QBRUe', 'Clark', 'Kent'),
(4,  'Frank', '$2y$12$LtJY9Q5.U3IK0jBeZokkueMzbbWjMVSE2uN49GkeX.fGGIA5QBRUe', 'Frank', 'Furter');

-- ROLES

INSERT INTO security_role (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO security_role (id, role_name, description) VALUES (2, 'ROLE_CSR', 'Customer Service Representative');

--USER ROLE MAPPING
INSERT INTO user_role(user_id, role_id) VALUES
 (1, 1), -- give admin ROLE_ADMIN
 (2, 2),  -- give Jane ROLE_CSR
 (3, 2),  -- give Mark ROLE_CSR
 (4, 1),  -- give Wally ROLE_ADMIN
 (4, 2);  -- give Wally ROLE_CSR
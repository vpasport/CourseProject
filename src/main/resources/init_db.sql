-- ADD IN CATEGORY

INSERT INTO category (category_id, description, name) VALUES (1, 'desc', 'category');

-- ADD IN EXECUTORS

INSERT INTO executors (id, order_order_id) VALUES (1, 2);
INSERT INTO executors (id, order_order_id) VALUES (2, 2);

INSERT INTO executors_order (executor_id, order_id) VALUES (1, 1);
INSERT INTO executors_order (executor_id, order_id) VALUES (2, 2);

-- ADD IN MESSAGES


-- ADD IN ORDERS

INSERT INTO orders (order_id, date, deadline, description, maxPrice, name, category_category_id, customer_user_id, executors_id, finalExecutor_user_id) VALUES (1, '2020-05-25', '2020-05-27', 'desc', 2, 'name1', 1, 3, NULL, NULL);
INSERT INTO orders (order_id, date, deadline, description, maxPrice, name, category_category_id, customer_user_id, executors_id, finalExecutor_user_id) VALUES (2, '2020-05-26', '2020-05-30', 'desc', 2000, 'name 2', 1, 3, NULL, 2);
-- ADD IN USERS

INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (1, 20, NULL, 'email1', 'fname1', '2020-05-25', 'login1', 'ff84abb1970b527abf2e96539a5944c844ef5c42df3c6479cda7d64543cdf3e1326878f388587c3aa8913b6aed65f408fa177236357607c22df75d1e1ef38d22', 'phone1', 0, 'ROLE_EXECUTOR', 'sname1', 'Male', 'math', NULL);
INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (2, 20, NULL, 'email2', 'fname2', '2020-05-25', 'login2', '385b5fb3512f87533f59837c26d0337dd8f3c7056e9607eb66e78fbc00fcdd52c6a5a3302eaf834df860379d8654924b00e5b34a059709c998bdc54772b794b2', 'phone2', 0, 'ROLE_EXECUTOR', 'sname2', 'Female', 'math', NULL);
INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (3, 20, NULL, 'email3', 'fname3', '2020-05-25', 'login3', 'b8fc591b94af95420b1613a23458c90b4025c6fcb8b786f6aefd4d706384a178bfe1c9389f2d068956b4d13d50d9b5401ac6572e4413b4cc47ab63ae098ff71e', 'phone3', 0, 'ROLE_CUSTOMER', 'sname3', 'Male', 'math', NULL);
INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (4, 20, NULL, 'email$', 'fname4', '2020-05-25', 'login4', 'b10efd62c6c76dbd202e540ae8103eb98a44c7baf8c08ada85f58c5f7ea3eccecd459132765fcd216e6a5c6ee37d06ed03158111e4db15b4251d3988c56c6c5e', 'phone4', 0, 'ROLE_ADMIN', 'sname4', 'Male', 'math', NULL);

ALTER TABLE category MODIFY category_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE executors MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE messages MODIFY message_id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE orders MODIFY orser_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE users MODIFY user_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

COMMIT;

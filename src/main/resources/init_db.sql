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

INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (1, 20, NULL, 'email1', 'fname1', '2020-05-25', 'login1', 'pass1', 'phone1', 0, 'Executor', 'sname1', 'Male', 'math', NULL);
INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (2, 20, NULL, 'email2', 'fname2', '2020-05-25', 'login2', 'pass2', 'phone2', 0, 'Executor', 'sname2', 'Female', 'math', NULL);
INSERT INTO users (user_id, age, description, email, firstName, lastActivity, login, password, phone, rating, role, secondName, sex, specialty, status) VALUES (3, 20, NULL, 'email3', 'fname3', '2020-05-25', 'login3', 'pass3', 'phone3', 0, 'Customer', 'sname3', 'Male', 'math', NULL);

ALTER TABLE category MODIFY category_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE executors MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE messages MODIFY message_id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE orders MODIFY orser_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE users MODIFY user_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

COMMIT;

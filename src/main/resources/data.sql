INSERT INTO `cards`.`users` (`email`, `password`, `role`) VALUES
('member1@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Member"),
('member2@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Member"),
('member3@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Member"),
('member4@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Member"),
('admin1@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Admin"),
('admin2@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Admin"),
('admin3@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Admin"),
('admin4@test.com', '$2a$10$7RFkSYxuKvCI.GlcW7de..HF7CfrLKl/jhfu7kAtxVNY.08N4t86W', "Admin");

INSERT INTO `cards`.`cards` (`color`, `date_of_creation`, `description`, `name`, `status`, `user_id`) VALUES
('#123456', '2024-01-28', 'a task', 'name1', 'To Do', 1),
('#123456', '2024-01-29', 'a task', 'name2', 'In Progress', 1),
('#123456', '2024-01-30', 'a task', 'name3', 'To Do', 2),
('#123456', '2024-01-30', 'a task', 'name4', 'In Progress', 3),
('#123456', '2024-01-29', 'a task', 'name5', 'To Do', 5),
('#123456', '2024-01-30', 'a task', 'name6', 'Done', 5),
('#123456', '2024-01-28', 'a task', 'name7', 'To Do', 6),
('#123456', '2024-01-27', 'a task', 'name8', 'In Progress', 7);


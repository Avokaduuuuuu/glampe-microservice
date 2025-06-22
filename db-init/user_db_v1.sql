CREATE DATABASE glampe_user_db;

USE glampe_user_db;


CREATE TABLE `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(255) DEFAULT "",
    `first_name` VARCHAR(255) DEFAULT "",
    `last_name` VARCHAR(255) DEFAULT "",
    `phone_number` VARCHAR(255) DEFAULT "",
    `address` VARCHAR(255) DEFAULT "",
    `dob` date,
    `role` ENUM('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'),
    `connection_id` varchar(255),
    `is_deleted` BOOLEAN DEFAULT FALSE,
    `avatar` VARCHAR(255) DEFAULT "",
    `last_login_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `is_owner` BOOLEAN DEFAULT false,
    `total_points` INT DEFAULT 0,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    
);

CREATE TABLE fcm_token(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`token` VARCHAR(500),
	`id_user` INT,
	`id_device` VARCHAR(255)

);


CREATE TABLE point_transaction(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`id_user` INT NOT NULL,
	`type` ENUM('Earn', 'Spend') NOT NULL,
	`amount` INT NOT NULL,
	`reason` VARCHAR(255),
	`id_booking` INT NOT NULL,
	`description` TEXT,
	`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `fcm_token`
	ADD FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

ALTER TABLE `point_transaction`
	ADD FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);


INSERT INTO `user` (
    `id`, `email`, `first_name`, `last_name`, `phone_number`, `address`, `role`, `created_at`, `is_deleted`, `dob`,
    `updated_at`, `avatar`, `is_owner`, `last_login_date`
) VALUES
    (1, 'admin@example.com', 'Admin', 'User', '1234567890', '123 Admin St', 'ROLE_ADMIN', NOW(), true, '2025-01-01', NOW(), 'https://i.loli.net/2020/11/19/LyN6JF7zZRskdIe.png', false, CURRENT_TIMESTAMP),
    (2, 'manager@example.com', 'Manager', 'User', '1234567891', '456 Manager Ave', 'ROLE_MANAGER', NOW(), true, '2025-01-01', NOW(), 'https://i.loli.net/2020/11/19/LyN6JF7zZRskdIe.png', false, CURRENT_TIMESTAMP),
    (3, 'user@example.com', 'John', 'Doe', '1234567892', '789 User Rd', 'ROLE_USER', NOW(), true, '2025-01-01', NOW(), 'https://i.loli.net/2020/11/19/LyN6JF7zZRskdIe.png', false, CURRENT_TIMESTAMP),
    (4, 'staff@example.com', 'Jane', 'Doe', '1234567893', '123 Staff Blvd', 'ROLE_USER', NOW(), true, '2025-01-01', NOW(), 'https://i.loli.net/2020/11/19/LyN6JF7zZRskdIe.png', false, CURRENT_TIMESTAMP);




